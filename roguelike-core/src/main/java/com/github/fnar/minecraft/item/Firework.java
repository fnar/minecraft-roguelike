package com.github.fnar.minecraft.item;

import com.google.common.collect.Lists;

import com.github.fnar.util.Color;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Firework extends RldBaseItem {

  private final List<Explosion> explosions = Lists.newArrayList();
  private FlightLength flightLength = FlightLength.SHORT;

  public static List<Color> randomColors(Random random) {
    int numberOfColours = random.nextInt(4) + 1;
    return IntStream.range(0, numberOfColours)
        .mapToObj(i -> randomColor(random))
        .collect(Collectors.toList());
  }

  private static Color randomColor(Random random) {
    return Color.HSLToColor(random.nextFloat(), (float) 1.0, (float) 0.5);
  }

  public List<Explosion> getExplosions() {
    return explosions;
  }

  public Firework withExplosion(Explosion explosion) {
    explosions.add(explosion);
    return this;
  }

  public FlightLength getFlightLength() {
    return flightLength;
  }

  public Firework withFlightLength(FlightLength flightLength) {
    this.flightLength = flightLength;
    return this;
  }

  @Override
  public ItemType getItemType() {
    return ItemType.FIREWORK;
  }

  public enum FlightLength {
    SHORT,
    MEDIUM,
    LONG,
    ;

    public static FlightLength chooseRandom(Random random) {
      FlightLength[] values = values();
      return values[random.nextInt(values.length)];
    }
  }

  public static class Explosion {

    private Shape shape = Shape.SMALL_BALL;
    private List<Color> colors = Lists.newArrayList();
    private boolean hasFlicker;
    private boolean hasTrail;

    public Shape getShape() {
      return shape;
    }

    public Explosion withShape(Shape shape) {
      this.shape = shape;
      return this;
    }

    public List<Color> getColors() {
      return colors;
    }

    public Explosion withColors(List<Color> colors) {
      this.colors.addAll(colors);
      return this;
    }

    public Explosion withColor(Color color) {
      this.colors.add(color);
      return this;
    }

    public boolean hasFlicker() {
      return hasFlicker;
    }

    public Explosion withFlicker(boolean hasFlicker) {
      this.hasFlicker = hasFlicker;
      return this;
    }

    public boolean hasTrail() {
      return hasTrail;
    }

    public Explosion withTrail(boolean hasTrail) {
      this.hasTrail = hasTrail;
      return this;
    }

    public enum Shape {
      SMALL_BALL,
      LARGE_BALL,
      STAR,
      FACE,
      BURST;
    }
  }
}
