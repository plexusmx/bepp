package bepp.com.bepp.openpay.services;

import bepp.com.bepp.openpay.exceptions.OpenpayServiceException;
import bepp.com.bepp.openpay.exceptions.ServiceUnavailableException;
import bepp.com.bepp.openpay.model.Card;
import bepp.com.bepp.openpay.model.Token;

/**
 * Created by charlie on 04/05/18.
 */

public class TokenService extends BaseService<Card, Token> {
    private static final String MERCHANT_TOKENS = "tokens";

    public TokenService(final String baseUrl, final String merchantId, final String apiKey) {
        super(baseUrl, merchantId, apiKey, Token.class);
    }

    public Token create(final Card card) throws OpenpayServiceException, ServiceUnavailableException {
        return this.post(MERCHANT_TOKENS, card);
    }

}