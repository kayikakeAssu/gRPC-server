package org.assu.service;

import io.grpc.stub.StreamObserver;
import org.assu.stubs.Bank;
import org.assu.stubs.BankServiceGrpc;

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
        super.getCurrencyStream(request, responseObserver);
    }

    @Override
    public StreamObserver<Bank.ConvertCurrencyRequest> performStream(StreamObserver<Bank.ConvertCurrencyResponse> responseObserver) {
        return super.performStream(responseObserver);
    }

    @Override
    public StreamObserver<Bank.ConvertCurrencyRequest> fullStream(StreamObserver<Bank.ConvertCurrencyResponse> responseObserver) {
        return super.fullStream(responseObserver);
    }


}
