package com.fauzighozali.mgamobile.api;

import androidx.annotation.Nullable;

import com.fauzighozali.mgamobile.jwt.TokenManager;
import com.fauzighozali.mgamobile.model.GetResponseToken;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;

public class CustomAuthenticator implements Authenticator {
    private TokenManager tokenManager;
    private static CustomAuthenticator INSTANCE;

    public CustomAuthenticator(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    static synchronized CustomAuthenticator getInstance(TokenManager tokenManager) {
        if (INSTANCE == null) {
            INSTANCE = new CustomAuthenticator(tokenManager);
        }
        return INSTANCE;
    }

    @Nullable
    @Override
    public Request authenticate(Route route, Response response) throws IOException {

        if (responseCount(response) >= 3) {
            return null;
        }

        GetResponseToken token = tokenManager.getToken();

        ApiService service = RetrofitBuilder.createService(ApiService.class);
        Call<GetResponseToken> call = service.refresh(token.getRefreshToken() + "a");
        retrofit2.Response<GetResponseToken> res = call.execute();

        if (res.isSuccessful()) {
            GetResponseToken newToken = res.body();
            tokenManager.saveToken(newToken);

            return response.request().newBuilder().header("Authorization", "Bearer " + res.body().getAccessToken()).build();
        }else {
            return null;
        }
    }

    private int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }
}
