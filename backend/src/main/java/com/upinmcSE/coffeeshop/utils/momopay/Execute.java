package com.upinmcSE.coffeeshop.utils.momopay;
import com.upinmcSE.coffeeshop.dto.request.momo.MoMoRequest;
import com.upinmcSE.coffeeshop.dto.response.momo.MoMoResponse;
import okhttp3.*;
import okio.Buffer;

import java.io.IOException;

public class Execute {

    OkHttpClient client = new OkHttpClient();

    public MoMoResponse sendToMoMo(String endpoint, String payload) {

        try {

            MoMoRequest httpRequest = new MoMoRequest("POST", endpoint, payload, "application/json");

            Request request = createRequest(httpRequest);

            LogUtils.debug("[HttpPostToMoMo] Endpoint:: " + httpRequest.getEndpoint() + ", RequestBody:: " + httpRequest.getPayload());

            Response result = client.newCall(request).execute();
            MoMoResponse response = new MoMoResponse(result.code(), result.body().string(), result.headers());

            LogUtils.info("[HttpResponseFromMoMo] " + response.toString());

            return response;
        } catch (Exception e) {
            LogUtils.error("[ExecuteSendToMoMo] "+ e);
        }

        return null;
    }

    public static Request createRequest(MoMoRequest request) {
        RequestBody body = RequestBody.create(MediaType.get(request.getContentType()), request.getPayload());
        return new Request.Builder()
                .method(request.getMethod(), body)
                .url(request.getEndpoint())
                .build();
    }

    public String getBodyAsString(Request request) throws IOException {
        Buffer buffer = new Buffer();
        RequestBody body = request.body();
        body.writeTo(buffer);
        return buffer.readUtf8();
    }
}
