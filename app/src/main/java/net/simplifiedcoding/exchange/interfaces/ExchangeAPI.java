package net.simplifiedcoding.exchange.interfaces;

import net.simplifiedcoding.exchange.models.Exchange;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ExchangeAPI {

    @GET("posts")
    Call<List<Exchange>> getExchange();
}
