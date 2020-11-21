package net.fabricmc.fabric.api.client.rendering.v1;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.dimension.DimensionType;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.mixin.client.rendering.SkyPropertiesAccessor;

public final class EnvironmentRenderers {
	private EnvironmentRenderers() {}

	@Environment(EnvType.CLIENT)
	private static final Map<RegistryKey<DimensionType>, SkyRenderer> SKY_RENDERERS = new IdentityHashMap<>();
	@Environment(EnvType.CLIENT)
	private static final Map<RegistryKey<DimensionType>, CloudRenderer> CLOUD_RENDERERS = new HashMap<>();
	@Environment(EnvType.CLIENT)
	private static final Map<RegistryKey<DimensionType>, WeatherRenderer> WEATHER_RENDERERS = new HashMap<>();

	/**
	 * Registers a custom sky renderer for a DimensionType
	 *
	 * @param key A RegistryKey for your Dimension Type
	 * @param renderer A {@link SkyRenderer} implementation
	 */
	@Environment(EnvType.CLIENT)
	public static void registerSkyRenderer(RegistryKey<DimensionType> key, SkyRenderer renderer) {
		Objects.requireNonNull(key);
		Objects.requireNonNull(renderer);
		SKY_RENDERERS.putIfAbsent(key, renderer);
	}

	/**
	 * Registers a custom rain and snow renderer for a DimensionType
	 *
	 * @param key A RegistryKey for your Dimension Type
	 * @param renderer A {@link WeatherRenderer} implementation
	 */
	@Environment(EnvType.CLIENT)
	public static void registerWeatherRenderer(RegistryKey<DimensionType> key, WeatherRenderer renderer) {
		Objects.requireNonNull(key);
		Objects.requireNonNull(renderer);
		WEATHER_RENDERERS.putIfAbsent(key, renderer);
	}

	/**
	 * Registers a custom sky property for a DimensionType
	 *
	 * @param key A RegistryKey for your Dimension Type
	 * @param properties The Dimension Type's sky properties
	 */
	@Environment(EnvType.CLIENT)
	public static void registerSkyProperty(RegistryKey<DimensionType> key, SkyProperties properties) {
		Objects.requireNonNull(key);
		Objects.requireNonNull(properties);
		((SkyPropertiesAccessor) properties).getIdentifierMap().put(key.getValue(), properties);
	}

	/**
	 * Registers a custom cloud renderer for a Dimension Type
	 *
	 * @param key A RegistryKey for your Dimension Type
	 * @param renderer A {@link CloudRenderer} implementation
	 */
	@Environment(EnvType.CLIENT)
	public static void registerCloudRenderer(RegistryKey<DimensionType> key, CloudRenderer renderer) {
		Objects.requireNonNull(key);
		Objects.requireNonNull(renderer);
		CLOUD_RENDERERS.putIfAbsent(key, renderer);
	}

	@Environment(EnvType.CLIENT)
	public static SkyRenderer getSkyRenderer(RegistryKey<DimensionType> key) {
		return SKY_RENDERERS.get(key);
	}

	@Environment(EnvType.CLIENT)
	public static CloudRenderer getCloudRenderer(RegistryKey<DimensionType> key) {
		return CLOUD_RENDERERS.get(key);
	}

	@Environment(EnvType.CLIENT)
	public static WeatherRenderer getWeatherRenderer(RegistryKey<DimensionType> key) {
		return WEATHER_RENDERERS.get(key);
	}

	@FunctionalInterface
	public interface SkyRenderer {
		void render(MinecraftClient world, MatrixStack matrices, float tickDelta);
	}

	@FunctionalInterface
	public interface WeatherRenderer {
		void render(MinecraftClient client, LightmapTextureManager manager, float tickDelta, double x, double y, double z);
	}

	@FunctionalInterface
	public interface CloudRenderer {
		void render(MinecraftClient client, MatrixStack matrices, float tickDelta, double cameraX, double cameraY, double cameraZ);
	}
}
