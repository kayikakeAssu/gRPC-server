package org.assu.service;

import io.grpc.stub.StreamObserver;
import org.assu.stubs.Bank;
import org.assu.stubs.BankServiceGrpc;

import java.util.Timer;
import java.util.TimerTask;

public class BankGrpcService extends BankServiceGrpc.BankServiceImplBase {

    @Override
    public void convert(Bank.ConvertCurrencyRequest request, StreamObserver<Bank.ConvertCurrencyResponse> responseObserver) {
        String currencyFrom = request.getCurrencyFrom();
        String currencyTo = request.getCurrencyTo();
        float amount = request.getAmount();

        Bank.ConvertCurrencyResponse response = Bank.ConvertCurrencyResponse
                .newBuilder()
                .setCurrencyFrom(currencyFrom)
                .setCurrencyTo(currencyTo)
                .setAmount(amount)
                .setResult((float) (amount * 14.12))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getCurrencyStream(Bank.ConvertCurrencyRequest request, StreamObserver<Bank.ConvertCurrencyResponse> responseObserver) {
        String currencyFrom = request.getCurrencyFrom();
        String currencyTo = request.getCurrencyTo();
        float amount = request.getAmount();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int counter = 0;
            @Override
            public void run() {
                Bank.ConvertCurrencyResponse response = Bank.ConvertCurrencyResponse.newBuilder()
                        .setCurrencyFrom(currencyFrom)
                        .setCurrencyTo(currencyTo)
                        .setResult((float) (amount * Math.random() * 10))
                        .build();

                responseObserver.onNext(response);
                ++counter;
                if (counter == 20) {
                    responseObserver.onCompleted();
                    timer.cancel();
                }

            }
        }, 1000, 1000);


    }

    @Override
    public StreamObserver<Bank.ConvertCurrencyRequest> performStream(StreamObserver<Bank.ConvertCurrencyResponse> responseObserver) {
        return new StreamObserver<Bank.ConvertCurrencyRequest>() {
            String currencyFrom;
            String currencyTo;
            float sum = 0;
            @Override
            public void onNext(Bank.ConvertCurrencyRequest convertCurrencyRequest) {
                sum+= convertCurrencyRequest.getAmount();
                currencyFrom = convertCurrencyRequest.getCurrencyFrom();
                currencyTo = convertCurrencyRequest.getCurrencyTo();
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                Bank.ConvertCurrencyResponse response = Bank.ConvertCurrencyResponse.newBuilder()
                        .setCurrencyFrom(currencyFrom)
                        .setCurrencyTo(currencyTo)
                        .setResult((float) (sum * Math.random() * 10))
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<Bank.ConvertCurrencyRequest> fullStream(StreamObserver<Bank.ConvertCurrencyResponse> responseObserver) {
        return new StreamObserver<Bank.ConvertCurrencyRequest>() {
            @Override
            public void onNext(Bank.ConvertCurrencyRequest convertCurrencyRequest) {
                Bank.ConvertCurrencyResponse response = Bank.ConvertCurrencyResponse
                        .newBuilder()
                        .setResult(convertCurrencyRequest.getAmount() * 54)
                        .build();

                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }


}
