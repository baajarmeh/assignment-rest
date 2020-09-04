package io.baay.assignment.filters;

import io.baay.assignment.models.Device;
import io.baay.assignment.utils.Constants;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PriceFilter implements Filter {

    @Override
    public List<Device> execute(List<Device> deviceList, Map<String, String> queryParams) {
        return deviceList.stream()
                .filter(p -> p.getRelease().getPriceEur() == getPrice(queryParams))
                .collect(Collectors.toList());
    }

    private int getPrice(Map<String, String> queryParams) {
        int price = 0;

        try {
            if (queryParams.containsKey(Constants.PRICE)) {
                price = Integer.parseInt(queryParams.get(Constants.PRICE));
            } else if (queryParams.containsKey(Constants.PRICE_EUR)) {
                price = Integer.parseInt(queryParams.get(Constants.PRICE_EUR));
            }
        } catch (NumberFormatException e) {
            price = 0;
        }

        return price;
    }
}
