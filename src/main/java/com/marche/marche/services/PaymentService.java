package com.marche.marche.services;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
@Service
public class PaymentService {

    // public PaymentIntent createPaymentIntent(Long amount) throws Exception {
    //     // Initialiser Stripe avec la clé API
    //     Stripe.apiKey = "sk_test_51PzdXHP9aFNqJB145aUNNWmROJAMdOGxzTDfpMUi4jRVREBTA0hw0rAJp3FYTJ23U47mcVn6XshWIE8TD6gBYaZk00JZikVkLh";

    //     // Créer un PaymentIntent avec les paramètres
    //     PaymentIntentCreateParams params =
    //         PaymentIntentCreateParams.builder()
    //             .setAmount(amount) // montant en centimes (ex: 5000 = 50.00)
    //             .setCurrency("mga") // définir la devise
    //             .addPaymentMethodType("card") // ajouter le type de méthode de paiement
    //             .build();

    //     // Retourner le PaymentIntent créé
    //     return PaymentIntent.create(params);
    // }

    public PaymentService() {
        // Initialiser Stripe avec votre clé secrète
        Stripe.apiKey = "sk_test_51PzdXHP9aFNqJB145aUNNWmROJAMdOGxzTDfpMUi4jRVREBTA0hw0rAJp3FYTJ23U47mcVn6XshWIE8TD6gBYaZk00JZikVkLh";
    }

    public Session createCheckoutSession(long prix) throws Exception {
        // Paramètres pour créer une session de paiement
        SessionCreateParams params = SessionCreateParams.builder()
            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl("https://example.com/success")
            .setCancelUrl("https://example.com/cancel")
            .addLineItem(
                SessionCreateParams.LineItem.builder()
                    .setPriceData(
                        SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency("mga")
                            .setUnitAmount(prix) // Montant en centimes
                            .setProductData(
                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                    .setName("Produits")
                                    .build()
                            )
                            .build()
                    )
                    .setQuantity(1L)
                    .build()
            )
            .build();

        // Créer la session de paiement
        return Session.create(params);
    }
}
