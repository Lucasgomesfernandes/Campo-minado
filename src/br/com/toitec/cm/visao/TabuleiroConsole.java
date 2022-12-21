package br.com.toitec.cm.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.toitec.cm.excecao.ExplosaoException;
import br.com.toitec.cm.excecao.SairException;
import br.com.toitec.cm.modelo.Tabuleiro;

public class TabuleiroConsole {

	private Tabuleiro tabuleiro;

	private Scanner entrada = new Scanner(System.in);

	public TabuleiroConsole(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;

		executarJogo();
	}

	private void executarJogo() {
		try {
			boolean continuar = true;

			while (continuar) {

				loopDoJogo();

				System.out.println("Outra partida? (S/n) ");
				String resposta = entrada.nextLine();

				if ("n".equalsIgnoreCase(resposta)) {
					continuar = false;
				} else {
					tabuleiro.reiniciar();
				}
			}
		} catch (SairException e) {
			System.out.println("Tchau!!");
		} finally {
			entrada.close();
		}

	}

	// explicação: se o objetivo do jogo nao foi alcançado, mostra o tabuleiro,
	// pegue os valores das cordenadas xy, transforma em um valor inteiro, verifica
	// se o jogador quer abrir ou marcar o campo, depois verifica o valor e realiza
	// oq o usuario pediu
	private void loopDoJogo() {
		try {

			while (!tabuleiro.objetivoAlcancado()) {
				System.out.println(tabuleiro);

				String digitado = capturarValorDigitado("Digite (x, y): ");

				// split separa a cada ","
				// trim tira os espaços em branco
				// se digitar algum texto vai gerar uma excessao
				Iterator<Integer> xy = Arrays.stream(digitado.split(",")).map(e -> Integer.parseInt(e.trim()))
						.iterator();

				digitado = capturarValorDigitado("1 - Abrir ou 2 - (Des)Marcar: ");

				if ("1".equals(digitado)) {
					tabuleiro.abrir(xy.next(), xy.next());
				} else if ("2".equals(digitado)) {
					tabuleiro.alternarMarcacao(xy.next(), xy.next());
				}

			}

			System.out.println(tabuleiro);
			System.out.println("Você ganhou !!");
		} catch (ExplosaoException e) {
			System.out.println(tabuleiro);
			System.out.println("Você perdeu !!");
		}

	}

	private String capturarValorDigitado(String texto) {
		System.out.println(texto);
		String digitado = entrada.nextLine();

		if ("sair".equalsIgnoreCase(digitado)) {
			throw new SairException();
		}

		return digitado;
	}
}
