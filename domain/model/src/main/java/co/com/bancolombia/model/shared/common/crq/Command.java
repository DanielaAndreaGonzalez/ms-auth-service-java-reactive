package co.com.bancolombia.model.shared.common.crq;

public record Command <P,C> (P payload, C context){ }
