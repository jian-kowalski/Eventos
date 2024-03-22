package br.com.jiankowalski.domain.pagination;

public record SearchQuery(
        int page,
        int perPage
) {
}
