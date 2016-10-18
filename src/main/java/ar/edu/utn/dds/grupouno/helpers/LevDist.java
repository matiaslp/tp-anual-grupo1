package ar.edu.utn.dds.grupouno.helpers;

import ar.edu.utn.dds.grupouno.helpers.LeerProperties;

public class LevDist {

	private static int minimo(int a, int b, int c) {
		if (a <= b && a <= c) {
			return a;
		}
		if (b <= a && b <= c) {
			return b;
		}
		return c;
	}

	public static boolean calcularDistancia(String str1, String str2) {
		if (str1 == null || str2 == null)
			return false;
		return (calcularDistancia(str1.toCharArray(), str2.toCharArray()) < Integer
				.valueOf(LeerProperties.getInstance().prop.getProperty("distanciaLeveinstein")));
	}

	private static int calcularDistancia(char[] str1, char[] str2) {
		int[][] distance = new int[str1.length + 1][str2.length + 1];

		for (int i = 0; i <= str1.length; i++) {
			distance[i][0] = i;
		}
		for (int j = 0; j <= str2.length; j++) {
			distance[0][j] = j;
		}
		for (int i = 1; i <= str1.length; i++) {
			for (int j = 1; j <= str2.length; j++) {
				distance[i][j] = minimo(distance[i - 1][j] + 1, distance[i][j - 1] + 1,
						distance[i - 1][j - 1] + ((str1[i - 1] == str2[j - 1]) ? 0 : 1));
			}
		}
		return distance[str1.length][str2.length];

	}
}
