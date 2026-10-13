package org.palatkaa.feature;

public final class FeatureEnableRejectedException extends RuntimeException {
   public FeatureEnableRejectedException(String message) {
      super(message, null, false, false);
   }
}

