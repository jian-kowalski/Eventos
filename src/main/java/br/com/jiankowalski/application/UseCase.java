package br.com.jiankowalski.application;

public interface UseCase<IN, OUT> {
    OUT execute(IN anIn);
}