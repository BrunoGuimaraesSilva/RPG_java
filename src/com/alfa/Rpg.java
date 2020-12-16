package com.alfa;

import java.sql.SQLException;
import java.util.Scanner;

public class Rpg {

	public static void main(String[] args) throws SQLException {
		PersonagemDao dao = new PersonagemDao();
		Scanner scanner = new Scanner(System.in);

		try {

			System.out.println("Cadastro de Personagem [1]");
			System.out.println("Jogar [2]");

			int menuInicial = 0;
			try {
				menuInicial = Integer.valueOf(scanner.nextLine());
			} catch (Exception e) {

			}

			if (menuInicial == 1) {
				System.out.println("Cadastrar 	[1]");
				System.out.println("Alterar   	[2]");
				System.out.println("Deletar   	[3]");

				int menuCadastro = Integer.valueOf(scanner.nextLine());
				switch (menuCadastro) {
				case 1:
					ClassePersonagem classe = null;
					int menuClasse = 0;
					System.out.println("Digite um Nome para seu Personagem: ");
					String menuNome = scanner.next();

					System.out.println("Escolha a Classe do seu Personagem: [1] ARQUEIRO | [2] MAGO | [3] GUERREIRO");						
					try {
						menuClasse = scanner.nextInt();
					} catch (Exception e) {

					}

					if (menuClasse == 1) {
						classe = ClassePersonagem.ARQUEIRO;
					} else if (menuClasse == 2) {
						classe = ClassePersonagem.MAGO;
					} else {
						classe = ClassePersonagem.GUERREIRO;
					}

					System.out.println("Insira a quantidade de Passos: ");
					int qtdPassos = scanner.nextInt();
					Personagem p = new Personagem(menuNome, classe, qtdPassos);
					dao.inserir(p);
					
					System.out.println("Personagem Criado com Sucesso!!");
					System.out.println("---------------------------------------------");
					dao.listarTodos().forEach(Personagem -> {
						System.out.println(Personagem.toString());
					});
					break;

				case 2:
					ClassePersonagem classes = null;
					int menu = 0;
					dao.listarTodos().forEach(Personagem -> {
						System.out.println(Personagem.toString());
					});
					System.out.println("--------------------------------------------");
					System.out.println("Escolha um Personagem pelo id para Editar: ");
					int pa = scanner.nextInt();
					
					System.out.println("Digite outro Nome para seu Personagem: ");
					String name = scanner.next();

					System.out.println("Escolha outra Classe para seu Personagem: [1] ARQUEIRO | [2] MAGO | [3] GUERREIRO");						
					try {
						menu = scanner.nextInt();
					} catch (Exception e) {

					}

					if (menu == 1) {
						classes = ClassePersonagem.ARQUEIRO;
					} else if (menu == 2) {
						classes = ClassePersonagem.MAGO;
					} else {
						classes = ClassePersonagem.GUERREIRO;
					}

					System.out.println("Insira outra quantidade de Passos: ");
					int pass = scanner.nextInt();
					
					dao.deletar(pa);
					
					p = new Personagem(name, classes, pass);
					
					dao.inserir(p);
					
					
					System.out.println("Alterado com Sucesso!!");
					System.out.println("--------------------------------------------");
					dao.listarTodos().forEach(Personagem -> {
						System.out.println(Personagem.toString());
					});
				
					break;

				case 3:
					dao.listarTodos().forEach(Personagem -> {
						System.out.println(Personagem.toString());
					});
					System.out.println("--------------------------------------------");
					System.out.println("Escolha um Personagem pelo id para Deletar: ");
					int pId = scanner.nextInt();
					dao.deletar(pId);

					System.out.println("Excluido com Sucesso!!");
					System.out.println("--------------------------------------------");
					dao.listarTodos().forEach(Personagem -> {
						System.out.println(Personagem.toString());
					});
					break;

				default:
					break;
				}

			} else if (menuInicial == 2) {
				System.out.println("Escolha um Personagem");

				dao.listarTodos().forEach(p -> {
					System.out.println(p.toString());
				});

				int menuPlayer = 0;
				try {
					menuPlayer = Integer.valueOf(scanner.nextLine());
				} catch (Exception e) {

				}

				jogar(dao.consultarPorId(menuPlayer));
				scanner.close();
			} else {
				System.out.println("Tchau brigado");

			}

		} catch (Exception e) {
			System.out.println(e.toString());
			System.out.println("Tchau brigado");
			scanner.close();
		}

		scanner.close();
		dao.getConnection().close();

	}

	private static void jogar(Personagem p) {

		try {
			System.out.println(p.golpeComum());
			System.out.println(p.golpeEspecial());

			if ((p.getPassos() % 2) == 1) {
				throw new ArmadilhaException();
			}

		} catch (ArmadilhaException e) {
			System.out.println(e.toString());
		}

	}

}
