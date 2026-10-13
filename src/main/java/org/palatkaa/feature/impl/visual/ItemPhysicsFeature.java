package org.palatkaa.feature.impl.visual;

import org.palatkaa.feature.Feature;
import org.palatkaa.feature.FeatureCategory;
import sdk.api.optimize.optimize;

@optimize
public final class ItemPhysicsFeature extends Feature {
   public ItemPhysicsFeature() {
      super("ItemPhysics", "Dropped items lie on the ground", FeatureCategory.VISUAL, -1);
   }
}

