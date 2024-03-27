package com.blackgear.geologicexpansion.common.worldgen.biome;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.world.level.biome.Climate;

import java.util.Arrays;
import java.util.List;

public class ParameterPointListBuilder {
    private final List<Climate.Parameter> temperatures = Lists.newArrayList();
    private final List<Climate.Parameter> humidities = Lists.newArrayList();
    private final List<Climate.Parameter> continentalnesses = Lists.newArrayList();
    private final List<Climate.Parameter> erosions = Lists.newArrayList();
    private final List<Climate.Parameter> depths = Lists.newArrayList();
    private final List<Climate.Parameter> weirdnesses = Lists.newArrayList();
    private final List<Long> offsets = Lists.newArrayList();

    /**
     * Adds temperature parameters to the list.
     * @param values values to be added.
     */
    public ParameterPointListBuilder temperature(Climate.Parameter... values) {
        this.temperatures.addAll(Arrays.asList(values));
        return this;
    }

    /**
     * Adds temperature values to the list.
     * @param values values to be added.
     */
    public ParameterPointListBuilder temperature(Temperature... values) {
        this.temperatures.addAll(Arrays.stream(values).map(temperature -> temperature.parameter).toList());
        return this;
    }

    /**
     * Adds humidity parameters to the list.
     * @param values values to be added.
     */
    public ParameterPointListBuilder humidity(Climate.Parameter... values) {
        this.humidities.addAll(Arrays.asList(values));
        return this;
    }

    /**
     * Adds humidity values to the list.
     * @param values values to be added.
     */
    public ParameterPointListBuilder humidity(Humidity... values) {
        this.humidities.addAll(Arrays.stream(values).map(humidity -> humidity.parameter).toList());
        return this;
    }

    /**
     * Adds continentalness parameters to the list.
     * @param values values to be added.
     */
    public ParameterPointListBuilder continentalness(Climate.Parameter... values) {
        this.continentalnesses.addAll(Arrays.asList(values));
        return this;
    }

    /**
     * Adds continentalness values to the list.
     * @param values values to be added.
     */
    public ParameterPointListBuilder continentalness(Continentalness... values) {
        this.continentalnesses.addAll(Arrays.stream(values).map(continentalness -> continentalness.parameter).toList());
        return this;
    }

    /**
     * Adds erosion parameters to the list.
     * @param values values to be added.
     */
    public ParameterPointListBuilder erosion(Climate.Parameter... values) {
        this.erosions.addAll(Arrays.asList(values));
        return this;
    }

    /**
     * Adds erosion values to the list.
     * @param values values to be added.
     */
    public ParameterPointListBuilder erosion(Erosion... values) {
        this.erosions.addAll(Arrays.stream(values).map(erosion -> erosion.parameter).toList());
        return this;
    }

    /**
     * Adds depth parameters to the list.
     * @param values values to be added.
     */
    public ParameterPointListBuilder depth(Climate.Parameter... values) {
        this.depths.addAll(Arrays.asList(values));
        return this;
    }

    /**
     * Adds depth values to the list.
     * @param values values to be added.
     */
    public ParameterPointListBuilder depth(Depth... values) {
        this.depths.addAll(Arrays.stream(values).map(depth -> depth.parameter).toList());
        return this;
    }

    /**
     * Adds weirdness parameters to the list.
     * @param values values to be added.
     */
    public ParameterPointListBuilder weirdness(Climate.Parameter... values) {
        this.weirdnesses.addAll(Arrays.asList(values));
        return this;
    }

    /**
     * Adds weirdness values to the list.
     * @param values values to be added.
     */
    public ParameterPointListBuilder weirdness(Weirdness... values) {
        this.weirdnesses.addAll(Arrays.stream(values).map(weirdness -> weirdness.parameter).toList());
        return this;
    }

    /**
     * Adds offset values to the list.
     * @param values values to be added.
     */
    public ParameterPointListBuilder offset(Float... values) {
        this.offsets.addAll(Arrays.stream(values).map(Climate::quantizeCoord).toList());
        return this;
    }

    /**
     * Adds offset values to the list.
     * @param values values to be added.
     */
    public ParameterPointListBuilder offset(Long... values) {
        this.offsets.addAll(Arrays.asList(values));
        return this;
    }

    /**
     * Builds a list of {@link Climate.ParameterPoint}
     * @return the parameter point list.
     */
    public List<Climate.ParameterPoint> build() {
        this.populateIfEmpty();
        ImmutableList.Builder<Climate.ParameterPoint> builder = new ImmutableList.Builder<>();

        this.temperatures.forEach(temperature -> {
            this.humidities.forEach(humidity -> {
                this.continentalnesses.forEach(continentalness -> {
                    this.erosions.forEach(erosion -> {
                        this.depths.forEach(depth -> {
                            this.weirdnesses.forEach(weirdness -> {
                                this.offsets.forEach(offset -> {
                                    builder.add(
                                        new Climate.ParameterPoint(
                                            temperature,
                                            humidity,
                                            continentalness,
                                            erosion,
                                            depth,
                                            weirdness,
                                            offset
                                        )
                                    );
                                });
                            });
                        });
                    });
                });
            });
        });

        return builder.build();
    }

    private void populateIfEmpty() {
        if (this.temperatures.isEmpty()) this.temperatures.add(Temperature.FULL_RANGE.parameter);
        if (this.humidities.isEmpty()) this.humidities.add(Humidity.FULL_RANGE.parameter);
        if (this.continentalnesses.isEmpty()) this.continentalnesses.add(Continentalness.FULL_RANGE.parameter);
        if (this.erosions.isEmpty()) this.erosions.add(Erosion.FULL_RANGE.parameter);
        if (this.depths.isEmpty()) this.depths.add(Depth.FULL_RANGE.parameter);
        if (this.weirdnesses.isEmpty()) this.weirdnesses.add(Weirdness.FULL_RANGE.parameter);
        if (this.offsets.isEmpty()) this.offsets.add(Climate.quantizeCoord(0.0F));
    }
}