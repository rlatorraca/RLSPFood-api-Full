package ca.com.rlsp.rlspfoodapi.domain.service;

import ca.com.rlsp.rlspfoodapi.domain.exception.GenericBusinessException;
import ca.com.rlsp.rlspfoodapi.domain.exception.OrderNotFoundException;
import ca.com.rlsp.rlspfoodapi.domain.model.*;
import ca.com.rlsp.rlspfoodapi.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class IssueOfOrderRegistrationService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestaurantRegistrationService restaurantRegistrationService;

    @Autowired
    private CityRegistrationService cityRegistrationService;

    @Autowired
    private UserRegistrationService userRegistrationService;

    @Autowired
    private ProductRegistrationService productRegistrationService;

    @Autowired
    private PaymentTypeRegistrationService paymentTypeRegistrationService;

    @Autowired
    private ProvinceRegistrationService provinceRegistrationService;

    @Transactional
    public Order issue(Order order) {
        orderValidate(order);
        itemsValidate(order);

        order.setDeliveryFee(order.getRestaurant().getDeliveryFee());
        order.calculateTotalValue();

        return orderRepository.save(order);
    }

    private void orderValidate(Order order) {
        City city = cityRegistrationService.findOrFail(order.getAddressDelivery().getCity().getId());
        User user = userRegistrationService.findOrFail(order.getUser().getId());
        Restaurant restaurant = restaurantRegistrationService.findOrFail(order.getRestaurant().getId());
        PaymentType paymentType = paymentTypeRegistrationService.findOrFail(order.getPaymentType().getId());
        Province province = provinceRegistrationService.findOrFail(city.getProvince().getId());

        order.getAddressDelivery().setCity(city);
        order.setUser(user);
        order.setRestaurant(restaurant);
        order.setPaymentType(paymentType);
        order.setTaxes(province.getTax().getTaxPercentual());


        if (restaurant.notAcceptPaymentType(paymentType)) {
            throw new GenericBusinessException(String.format("Payment type '%s' not accept for this restaurant.",
                    paymentType.getName()));
        }
    }

    private void itemsValidate(Order order) {
        order.getOrderItems().forEach(item -> {
            Product product = productRegistrationService.findOrFail(
                    order.getRestaurant().getId(), item.getProduct().getId());

            item.setOrder(order);
            item.setProduct(product);
            item.setUnitPrice(product.getPrice());
        });
    }

    public Order findOrFail(String orderCode) {
        return orderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new OrderNotFoundException(orderCode));
    }
}
