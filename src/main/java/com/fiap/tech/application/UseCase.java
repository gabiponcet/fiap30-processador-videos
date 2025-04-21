package com.fiap.tech.application;

public abstract class UseCase<IN> {

    public abstract void execute(IN anIN);
}