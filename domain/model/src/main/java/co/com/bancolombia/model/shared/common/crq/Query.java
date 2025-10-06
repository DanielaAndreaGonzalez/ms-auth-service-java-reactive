package co.com.bancolombia.model.shared.common.crq;

public record Query<P, C>(P payload, C context) {}
