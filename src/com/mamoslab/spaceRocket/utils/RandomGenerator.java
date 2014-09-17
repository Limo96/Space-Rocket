package com.mamoslab.spaceRocket.utils;

import java.util.Random;

public class RandomGenerator {
	public static Random newRandom() {
		return new Random(System.currentTimeMillis() * 1000000 + System.nanoTime());
	}
}
