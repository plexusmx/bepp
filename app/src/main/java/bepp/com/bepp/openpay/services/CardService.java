package bepp.com.bepp.openpay.services;

import bepp.com.bepp.openpay.exceptions.OpenpayServiceException;
import bepp.com.bepp.openpay.exceptions.ServiceUnavailableException;
import bepp.com.bepp.openpay.model.Card;

/**
 * Created by charlie on 04/05/18.
 */

public class CardService extends BaseService<Card, Card> {

    private static final String MERCHANT_CARDS = "cards";
    private static final String CUSTOMER_CARDS = "customers/%s/cards";

    public CardService(final String baseUrl, final String merchantId, final String apiKey) {
        super(baseUrl, merchantId, apiKey, Card.class);
    }

    public Card create(final Card card, final String customerId) throws OpenpayServiceException,
            ServiceUnavailableException {
        String resourceUrl = MERCHANT_CARDS;
        if (customerId != null && !customerId.equals("")) {
            resourceUrl = String.format(CUSTOMER_CARDS, customerId);
        }

        return this.post(resourceUrl, card);
    }

}