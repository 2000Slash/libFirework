<p align="center">
  <img src="https://user-images.githubusercontent.com/10107972/206851252-65843d77-c8a8-4c00-8f7f-8b7f14ee89fe.png" alt="libFirework" height="300px" />
</p>
<span align="center">

# libFirework 
</span>
A Minecraft library to create custom fireworks. What used to be a simple shitpost about amogus in minecraft evolved into this.

## How to use

To use the library, just add it as a gradle dependency 
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    ...
    modImplementation 'com.github.2000Slash:libFirework:v1.0.0'
}

```

Then you can register `CustomRocketTypes` using `com.github.libfirework.LibFirework.registerCustomRocketType()`. If you don't want to implement a `CustomRocketTypes` all by yourself you should really use `com.github.libfirework.types.simple.CustomRocketTypeBuilder`. You can use it like this

```java
    @Override
    public void onInitialize() {
        LibFirework.registerCustomRocketType(new CustomRocketTypeBuilder(new Identifier("libfirework", "triangle"), Items.NETHERITE_INGOT).drawLines(new double[][]{{0.0, 1.0}, {1.0, 0.0}, {-1.0, 0.0}, {0.0, 1.0}}, 50).build());
        LibFirework.registerCustomRocketType(new CustomRocketTypeBuilder(new Identifier("libfirework", "ball"), Items.SNOWBALL).fillBall(5, 1).build());
        LibFirework.registerCustomRocketType(new CustomRocketTypeBuilder(new Identifier("libfirework", "burst"), Items.FIRE_CHARGE).burst(100).build());
        LibFirework.registerCustomRocketType(new CustomRocketTypeBuilder(new Identifier("libfirework", "amogus"), Items.REDSTONE).explodeSvg(TestLibfirework.class.getClassLoader().getResourceAsStream("amogus.svg"), "amogus.svg", 5.f, 1/500.f, new float[]{-0.8f, 1.f}).build());
    }
```

These examples are used straight from [here](src/test/java/com/github/libfirework/TestLibfirework.java)

## Custom svgs
<img src="https://user-images.githubusercontent.com/10107972/206926741-c21c1d75-67f9-4b74-91ca-2cf6ee802c7a.png" alt="drawing" width="500"/>
If you want to use custom svgs, you can use the `CustomRocketTypeBuilder.explodeSvg()`. To do this, you have to supply it with an input stream of the svg. The svg should only contain one path and it should be the first child of the root. If you want an example, look [here](src/test/resources/amogus.svg). You also have to supply pointDistance, scale, and origin. The pointDistance regulate the number of particles that are generated. The scale and origin define how big the svg is drawn and what direction it moves to.
