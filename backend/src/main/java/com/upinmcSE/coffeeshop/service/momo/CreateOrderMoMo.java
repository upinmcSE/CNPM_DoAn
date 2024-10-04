package com.upinmcSE.coffeeshop.service.momo;

import com.upinmcSE.coffeeshop.configuration.momopay.CustomEnvironment;
import com.upinmcSE.coffeeshop.constants.Parameter;
import com.upinmcSE.coffeeshop.dto.request.momo.PaymentMoMoRequest;
import com.upinmcSE.coffeeshop.dto.response.momo.MoMoResponse;
import com.upinmcSE.coffeeshop.dto.response.momo.PaymentMoMoResponse;
import com.upinmcSE.coffeeshop.enums.momo.Language;
import com.upinmcSE.coffeeshop.enums.momo.RequestType;
import com.upinmcSE.coffeeshop.exception.MoMoException;
import com.upinmcSE.coffeeshop.utils.momopay.Encoder;
import com.upinmcSE.coffeeshop.utils.momopay.LogUtils;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderMoMo extends AbstractProcess<PaymentMoMoRequest, PaymentMoMoResponse> {
    public CreateOrderMoMo(CustomEnvironment environment) {
        super(environment);
    }

    /**
     * Capture MoMo Process on Payment Gateway
     *
     * @param amount
     * @param extraData
     * @param orderInfo
     * @param env       name of the environment (dev or prod)
     * @param orderId   unique order ID in MoMo system
     * @param requestId request ID
     * @param returnURL URL to redirect customer
     * @param notifyURL URL for MoMo to return transaction status to merchant
     * @return PaymentResponse
     **/

    public static PaymentMoMoResponse process(
            CustomEnvironment env, String orderId, String requestId, String amount,
            String orderInfo, String returnURL, String notifyURL, String extraData,
            RequestType requestType, Boolean autoCapture
    ) throws MoMoException
    {
        try {
            CreateOrderMoMo m2Processor = new CreateOrderMoMo(env);

            PaymentMoMoRequest request = m2Processor.createPaymentCreationRequest(orderId, requestId, amount, orderInfo, returnURL, notifyURL, extraData, requestType, autoCapture);
            PaymentMoMoResponse captureMoMoResponse = m2Processor.execute(request);

            return captureMoMoResponse;
        } catch (Exception exception) {
            LogUtils.error("[CreateOrderMoMoProcess] "+ exception);
        }
        return null;
    }

    @Override
    public PaymentMoMoResponse execute(PaymentMoMoRequest request) throws MoMoException {
        try {
            // Chuyển đổi request thành JSON
            String payload = getGson().toJson(request, PaymentMoMoRequest.class);
            // Gọi MoMo API
            MoMoResponse response = execute.sendToMoMo(environment.getMomoEndpoint().getEndpoint(), payload);

            // Kiểm tra trạng thái phản hòi
            if (response.getStatus() != 200) {
                throw new MoMoException("[PaymentResponse] [" + request.getOrderId() + "] -> Error API");
            }
            // Ghi log dữ liệu nhận được
            System.out.println("uweryei7rye8wyreow8: "+ response.getData());

            PaymentMoMoResponse captureMoMoResponse = getGson().fromJson(response.getData(), PaymentMoMoResponse.class);
            String responserawData = Parameter.REQUEST_ID + "=" + captureMoMoResponse.getRequestId() +
                    "&" + Parameter.ORDER_ID + "=" + captureMoMoResponse.getOrderId() +
                    "&" + Parameter.MESSAGE + "=" + captureMoMoResponse.getMessage() +
                    "&" + Parameter.PAY_URL + "=" + captureMoMoResponse.getPayUrl() +
                    "&" + Parameter.RESULT_CODE + "=" + captureMoMoResponse.getResultCode();

            // Lưu trữ raw data cho việc ghi log
            LogUtils.info("[PaymentMoMoResponse] rawData: " + captureMoMoResponse);

            return captureMoMoResponse;

        } catch (Exception exception) {
            LogUtils.error("[PaymentMoMoResponse] "+ exception);
            throw new IllegalArgumentException("Invalid params capture MoMo Request");
        }
    }

    /**
     * @param orderId
     * @param requestId
     * @param amount
     * @param orderInfo
     * @param returnUrl
     * @param notifyUrl
     * @param extraData
     * @return
     */
    public PaymentMoMoRequest createPaymentCreationRequest(String orderId, String requestId, String amount, String orderInfo,
                                                           String returnUrl, String notifyUrl, String extraData, RequestType requestType, Boolean autoCapture) {

        try {
            String requestRawData = new StringBuilder()
                    .append(Parameter.ACCESS_KEY).append("=").append(partnerInfo.getAccessKey()).append("&")
                    .append(Parameter.AMOUNT).append("=").append(amount).append("&")
                    .append(Parameter.EXTRA_DATA).append("=").append(extraData).append("&")
                    .append(Parameter.IPN_URL).append("=").append(notifyUrl).append("&")
                    .append(Parameter.ORDER_ID).append("=").append(orderId).append("&")
                    .append(Parameter.ORDER_INFO).append("=").append(orderInfo).append("&")
                    .append(Parameter.PARTNER_CODE).append("=").append(partnerInfo.getPartnerCode()).append("&")
                    .append(Parameter.REDIRECT_URL).append("=").append(returnUrl).append("&")
                    .append(Parameter.REQUEST_ID).append("=").append(requestId).append("&")
                    .append(Parameter.REQUEST_TYPE).append("=").append(requestType.getRequestType())
                    .toString();

            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());
            LogUtils.debug("[PaymentRequest] rawData: " + requestRawData + ", [Signature] -> " + signRequest);

            return new PaymentMoMoRequest(partnerInfo.getPartnerCode(), orderId, requestId, Language.EN, orderInfo, Long.valueOf(amount), "test MoMo", null, requestType,
                    returnUrl, notifyUrl, "test store ID", extraData, null, autoCapture, null, signRequest);
        } catch (Exception e) {
            LogUtils.error("[PaymentRequest] "+ e);
        }

        return null;
    }
}
