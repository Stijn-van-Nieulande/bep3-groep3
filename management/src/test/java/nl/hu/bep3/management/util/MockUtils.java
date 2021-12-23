package nl.hu.bep3.management.util;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

import java.io.IOException;

public final class MockUtils {

  private MockUtils() {
    throw new UnsupportedOperationException("This class cannot be instantiated");
  }

  public static String loadPayload(final String path) throws IOException {
    return copyToString(
        MockUtils.class.getClassLoader().getResourceAsStream(path), defaultCharset());
  }
}
