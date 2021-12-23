package nl.hu.bep3.management;

import com.github.tomakehurst.wiremock.WireMockServer;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import nl.hu.bep3.dish.application.response.DishOutDto;
import nl.hu.bep3.dish.application.response.IngredientAmountOutDto;
import nl.hu.bep3.dish.application.response.MenuDto;
import nl.hu.bep3.dish.domain.AmountUnit;
import nl.hu.bep3.kitchen.application.request.ProductDtoIn;
import nl.hu.bep3.kitchen.application.response.IngredientDto;
import nl.hu.bep3.kitchen.application.response.StockDto;
import nl.hu.bep3.kitchen.domain.FoodAllergy;
import nl.hu.bep3.kitchen.domain.Kitchen;
import nl.hu.bep3.kitchen.domain.ProductInStock;
import nl.hu.bep3.management.proxy.KitchenServiceProxy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

  @Autowired
  @Qualifier("mockKitchenService")
  private WireMockServer mockKitchenService;

  @Autowired private KitchenServiceProxy kitchenServiceProxy;

  @BeforeEach
  void setUp() throws IOException {
    KitchenMocks.setupMockGetKitchenStockResponse(this.mockKitchenService);
    KitchenMocks.setupMockPatchKitchenStockResponse(this.mockKitchenService);
    KitchenMocks.setupMockGetKitchenMenuResponse(this.mockKitchenService);
  }

  @Test
  @DisplayName("After getting the stock, the stock should be returned")
  public void whenGetStock_thenStockShouldBeReturned() {
    final UUID kitchenId = UUID.fromString("0498E5DC-CDF5-4F85-BBF5-2A39360638BF");

    // Make the api call.
    final StockDto response = this.kitchenServiceProxy.getStock(kitchenId);

    // Test if the kitchen id is not null.
    Assertions.assertNotNull(response.kitchen);

    // Test if the kitchen id is the same as provided.
    Assertions.assertEquals(kitchenId, response.kitchen);
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

    // Test if the stock response ingredient list contains te expected ingredient.
    // Assertions.assertTrue(response.ingredientList.contains(kaas));
    Assertions.assertTrue(
        response.ingredientList.stream()
            .allMatch(
                ingredientDto ->
                    ingredientDto.ingredientName.equals(kaas.ingredientName)
                        && ingredientDto.amount == kaas.amount
                        && ingredientDto.amountUnit.equals(kaas.amountUnit)
                        && ingredientDto.allergies.containsAll(kaas.allergies)));
  }

  @Test
  @DisplayName("After patching the stock, the stock should be updated")
  public void whenPatchStock_thenStockShouldBeUpdated() {
    final UUID kitchenId = UUID.fromString("0498E5DC-CDF5-4F85-BBF5-2A39360638BF");
    final UUID ingredientId = UUID.fromString("4f78d871-f3b0-47aa-9fe0-aecadd05474d");

    // The ingredient used for the patch.
    final ProductDtoIn patchDto = new ProductDtoIn();
    patchDto.name = "Kaas";
    patchDto.amount = 80;

    // Make the api call.
    final Kitchen response =
        this.kitchenServiceProxy.updateStock(kitchenId, ingredientId, patchDto);

    // Test if the kitchen id is not null.
    Assertions.assertNotNull(response.getId());

    // Test if the kitchen id is the same as provided.
    Assertions.assertEquals(kitchenId, response.getId());

    // Find the response ingredient by name of the patch ingredient.
    final ProductInStock responseIngredient =
        response.getStorage().getIngredientInStock().stream()
            .filter(
                productInStock ->
                    productInStock.getId() != null && productInStock.getId().equals(ingredientId))
            .findFirst()
            .orElse(null);

    // Test if the response ingredient is not null.
    Assertions.assertNotNull(responseIngredient);

    // Test if the amount of the response ingredient is the same as the patch ingredient.
    Assertions.assertEquals(responseIngredient.getAmount(), patchDto.amount);
  }

  @Test
  @DisplayName("After getting the menu, the menu should be returned")
  public void whenGetMenu_thenMenuShouldBeReturned() {
    final UUID kitchenId = UUID.fromString("0498E5DC-CDF5-4F85-BBF5-2A39360638BF");
    final UUID menuId = UUID.fromString("0498E5DC-CDF5-4F85-BBF5-2A39360638BF");

    // Make the api call.
    final MenuDto response = this.kitchenServiceProxy.getMenu(kitchenId);

    // Test if the menu is not empty.
    Assertions.assertFalse(response.menu.isEmpty());

    // Test if the kitchen id is the same as provided.
    // Fixme
    // Assertions.assertEquals(kitchenId, response.);
  }

  @Test
  @DisplayName("After getting the menu, the correct dishes should be returned")
  public void whenGetMenu_thenTheCorrectDishesShouldBeReturned() {
    final UUID kitchenId = UUID.fromString("0498E5DC-CDF5-4F85-BBF5-2A39360638BF");
    final UUID menuId = UUID.fromString("0498E5DC-CDF5-4F85-BBF5-2A39360638BF");

    // The expected dish.
    final DishOutDto kaasplank = new DishOutDto();
    kaasplank.id = menuId;
    kaasplank.name = "Kaasplank";
    kaasplank.price = 10.0;

    final IngredientAmountOutDto kaas = new IngredientAmountOutDto();
    kaas.amount = 100;
    kaas.unit = AmountUnit.GRAM.toString();
    kaas.ingredientName = "Kaas";
    kaas.allergies = List.of(nl.hu.bep3.dish.domain.FoodAllergy.MILK);

    kaasplank.ingredients.add(kaas);

    // Make the api call.
    final MenuDto response = this.kitchenServiceProxy.getMenu(kitchenId);

    // Test if the first dish id is the expected dish id.
    Assertions.assertEquals(response.menu.get(0).id, kaasplank.id);

    // Test if the first dish ingredient list is as expected.
    Assertions.assertTrue(
        response.menu.get(0).ingredients.stream()
            .allMatch(
                ingredientDto ->
                    ingredientDto.ingredientName.equals(kaas.ingredientName)
                        && ingredientDto.amount == kaas.amount
                        && ingredientDto.unit.equals(kaas.unit)
                        && ingredientDto.allergies.containsAll(kaas.allergies)));
  }
}
