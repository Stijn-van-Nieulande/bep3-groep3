package nl.hu.bep3.management;

import com.github.tomakehurst.wiremock.WireMockServer;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import nl.hu.bep3.kitchen.application.response.IngredientDto;
import nl.hu.bep3.kitchen.application.response.StockDto;
import nl.hu.bep3.kitchen.domain.AmountUnit;
import nl.hu.bep3.kitchen.domain.FoodAllergy;
import nl.hu.bep3.management.proxy.KitchenServiceProxy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WireMockConfig.class})
@DisplayName("Kitchen service integration test")
class KitchenServiceProxyIntegrationTest {

  @Autowired private WireMockServer mockKitchenService;
  @Autowired private KitchenServiceProxy kitchenServiceProxy;

  @BeforeEach
  void setUp() throws IOException {
    KitchenMocks.setupMockGetKitchenStockResponse(this.mockKitchenService);
    KitchenMocks.setupMockPatchKitchenStockResponse(this.mockKitchenService);
  }

  @Test
  @DisplayName("After getting the stock, the stock should be returned")
  public void whenGetStock_thenStockShouldBeReturned() {
    final UUID kitchenUuid = UUID.fromString("0498E5DC-CDF5-4F85-BBF5-2A39360638BF");
    final StockDto stockDto = this.kitchenServiceProxy.getStock(kitchenUuid);

    // Test if the kitchen id is not null.
    Assertions.assertNotNull(stockDto.kitchen);

    // Test if the kitchen id is the same as provided.
    Assertions.assertEquals(kitchenUuid, stockDto.kitchen);
  }

  @Test
  @DisplayName("After getting the stock, the correct ingredients should be returned")
  public void whenGetStock_thenTheCorrectIngredientsShouldBeReturned() {
    final UUID kitchenUuid = UUID.fromString("0498E5DC-CDF5-4F85-BBF5-2A39360638BF");

    // The expected ingredient.
    final IngredientDto kaas = new IngredientDto();
    kaas.amount = 100;
    kaas.amountUnit = AmountUnit.GRAM;
    kaas.ingredientName = "Kaas";
    kaas.allergies = List.of(FoodAllergy.MILK);

    // Make the api call.
    final StockDto response = this.kitchenServiceProxy.getStock(kitchenUuid);

    // Test if the stick response ingredient list contains te expected ingredient.
    Assertions.assertTrue(response.ingredientList.contains(kaas));
  }

  @Test
  @DisplayName("After patching the stock, the stock should be updated")
  public void whenPatchStock_thenStockShouldBeUpdated() {
    final UUID kitchenUuid = UUID.fromString("0498E5DC-CDF5-4F85-BBF5-2A39360638BF");

    // The ingredient used for the patch.
    final IngredientDto patchDto = new IngredientDto();
    patchDto.ingredientName = "Kaas";
    patchDto.amount = 80;

    // Make the api call.
    final StockDto response = this.kitchenServiceProxy.updateStock(kitchenUuid, patchDto);

    // Test if the kitchen id is not null.
    Assertions.assertNotNull(response.kitchen);

    // Test if the kitchen id is the same as provided.
    Assertions.assertEquals(kitchenUuid, response.kitchen);

    // Find the response ingredient by name of the patch ingredient.
    final IngredientDto responseIngredient =
        response.ingredientList.stream()
            .filter(ingredientDto -> ingredientDto.ingredientName.equals(patchDto.ingredientName))
            .findFirst()
            .orElse(null);

    // Test if the response ingredient is not null.
    Assertions.assertNotNull(responseIngredient);

    // Test if the amount of the response ingredient is the same as the patch ingredient.
    Assertions.assertEquals(responseIngredient.amount, patchDto.amount);
  }
}
