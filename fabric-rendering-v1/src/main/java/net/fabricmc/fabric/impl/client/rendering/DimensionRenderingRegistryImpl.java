/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.fabric.impl.client.rendering;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;

import org.jetbrains.annotations.Nullable;

import net.minecraft.client.render.SkyProperties;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry.CloudRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry.SkyRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry.WeatherRenderer;
import net.fabricmc.fabric.mixin.client.rendering.SkyPropertiesAccessor;

public final class DimensionRenderingRegistryImpl {
	private static final Map<RegistryKey<World>, SkyRenderer> SKY_RENDERERS = new IdentityHashMap<>();
	private static final Map<RegistryKey<World>, CloudRenderer> CLOUD_RENDERERS = new HashMap<>();
	private static final Map<RegistryKey<World>, WeatherRenderer> WEATHER_RENDERERS = new HashMap<>();

	public static void registerSkyRenderer(RegistryKey<World> key, DimensionRenderingRegistry.SkyRenderer renderer) {
		Objects.requireNonNull(key);
		Objects.requireNonNull(renderer);

		if (SKY_RENDERERS.containsKey(key)) {
			throw new IllegalStateException("This world already has a registered SkyRenderer.");
		} else {
			SKY_RENDERERS.put(key, renderer);
		}
	}

	public static void registerWeatherRenderer(RegistryKey<World> key, WeatherRenderer renderer) {
		Objects.requireNonNull(key);
		Objects.requireNonNull(renderer);

		if (WEATHER_RENDERERS.containsKey(key)) {
			throw new IllegalStateException("This world already has a registered WeatherRenderer.");
		} else {
			WEATHER_RENDERERS.put(key, renderer);
		}
	}

	public static void registerSkyProperties(Identifier key, SkyProperties properties) {
		Objects.requireNonNull(key);
		Objects.requireNonNull(properties);
		//The map containing all skyproperties returns a default if null so a null checkdoesn't work.

		if (SkyPropertiesAccessor.getIdentifierMap().containsKey(key)) {
			throw new IllegalStateException("This id already has a registered skyproperties.");
		} else {
			SkyPropertiesAccessor.getIdentifierMap().put(key, properties);
			System.out.println(SkyPropertiesAccessor.getIdentifierMap().get(key));
		}
	}

	public static void registerCloudRenderer(RegistryKey<World> key, CloudRenderer renderer) {
		Objects.requireNonNull(key);
		Objects.requireNonNull(renderer);

		if (CLOUD_RENDERERS.containsKey(key)) {
			throw new IllegalStateException("This world already has a registered CloudRenderer.");
		} else {
			CLOUD_RENDERERS.put(key, renderer);
		}
	}

	@Nullable
	public static SkyRenderer getSkyRenderer(RegistryKey<World> key) {
		return SKY_RENDERERS.get(key);
	}

	@Nullable
	public static CloudRenderer getCloudRenderer(RegistryKey<World> key) {
		return CLOUD_RENDERERS.get(key);
	}

	@Nullable
	public static WeatherRenderer getWeatherRenderer(RegistryKey<World> key) {
		return WEATHER_RENDERERS.get(key);
	}

	@Nullable
	public static SkyProperties getSkyProperties(Identifier key) {
		return SkyPropertiesAccessor.getIdentifierMap().get(key);
	}
}
