# Sistema de Locação de Quartos Compartilhados \- Host (MVP)

Aplicação web multiusuário desenvolvida em **Java (Spring Boot \+ Thymeleaf \+ JPA/Hibernate \+ Spring Security)** seguindo o Documento de Requisitos de Software (SRS) da disciplina de Engenharia de Software (UNESP FCT \- Presidente Prudente).

---

## 1\. Funcionalidades Implementadas no MVP

- **Cadastro, Autenticação e Perfis:**  
  - Cadastro de novos usuários com armazenamento de senha em hash BCrypt.  
  - Autenticação e controle de acesso por perfil com Spring Security.  
  - Habilitação do perfil de **Anunciante** mediante preenchimento dos dados adicionais obrigatórios: CPF/CNPJ, telefone principal, comprovante de residência e chave PIX.  
- **Cadastro de Imóveis e Quartos por Anunciantes:**  
  - Painel exclusivo para usuários com perfil `ANUNCIANTE` (`/anunciante/painel`).  
  - Cadastro de **Imóveis** (título, endereço privado, bairro, cidade, CEP e descrição).  
  - Cadastro de **Quartos** vinculados aos imóveis do anunciante (identificação, tipo, capacidade, comodidades, valor mensal, regras, status e versão `@Version`).  
- **Catálogo Público sem Cadastro:**  
  - Consulta pública de todos os quartos com status `DISPONIVEL` acessível sem necessidade de login (`/` ou `/catalogo`).  
  - Exibição apenas da localização aproximada (`Bairro - Cidade`), preservando a privacidade do endereço exato do imóvel.

---

## 2\. Arquitetura e Padrões de Projeto

O código-fonte está organizado no pacote `br.unesp.fct.host` seguindo o padrão **MVC**:

- `model`: Entidades JPA (`EntidadeBase`, `Usuario`, `Imovel`, `Quarto`) e enumerações (`PerfilUsuario`, `StatusUsuario`, `TipoQuarto`, `StatusQuarto`), aplicando **Abstração, Herança, Encapsulamento e Polimorfismo**.  
- `dto`: Objetos de formulário com validações Jakarta Bean Validation e mensagens em português.  
- `repository`: Interfaces Spring Data JPA (`UsuarioRepository`, `ImovelRepository`, `QuartoRepository`).  
- `service` / `service.impl`: Interfaces de contrato de serviço e implementações transacionais com log nativo `java.util.logging.Logger`.  
- `controller`: Controladores MVC (`CatalogoController`, `UsuarioController`, `AnuncianteController`) e tratamento centralizado de exceções (`GlobalExceptionHandler`).  
- `config`: Configuração do Spring Security (`SecurityConfig`) e carga inicial de dados para demonstração (`DataInitializer`).

---

## 3\. Instruções de Instalação e Execução

### Pré-requisitos

- **Java JDK 17 ou superior** instalado (testado também com JDK 20+).  
- Não é necessário instalar Maven ou banco de dados externo: o projeto inclui o **Maven Wrapper (`mvnw`)** e o banco relacional **H2 Database** configurado em arquivo (`./data/hostdb`).

### Como Executar

No terminal (PowerShell ou CMD), acesse a pasta `host-mvp` e execute:

```powershell
cd "localondeestaapasta\host-mvp"
.\mvnw.cmd spring-boot:run
```

&nbsp;

### Como Executar(alternativo)

No painel lateral esquerdo (Project), expanda: src → main → java → br.unesp.fct.host Abra a classe principal HostApplication.java. Clique no ícone verde de Play (▶) ao lado de public class HostApplication (ou pressione Shift \+ F10) e escolha Run 'HostApplication'.

Após iniciar, acesse no navegador:

- **Catálogo Público:** `http://localhost:8080/catalogo`  
- **Login:** `http://localhost:8080/login`  
- **Cadastro:** `http://localhost:8080/cadastro`  
- **Console do Banco H2:** `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:file:./data/hostdb`)

### Contas de Demonstração Pré-Cadastradas

| Perfil | E-mail | Senha |
| :---- | :---- | :---- |
| **Anunciante** | `anunciante@host.com` | `Senha@123456` |
| **Usuário Comum** | `estudante@host.com` | `Senha@123456` |

&nbsp;

### Como Executar os Testes Automatizados

```powershell
.\mvnw.cmd test
```

&nbsp;

---

## 4\. Perguntas Frequentes (FAQ) e Suporte

1. **Como um usuário comum pode anunciar imóveis e quartos?** Após criar sua conta e fazer login, clique em **"Quero Anunciar"** (ou acesse `/perfil`), preencha seu CPF/CNPJ, telefone, comprovante de residência e chave PIX e clique em **"Habilitar Perfil de Anunciante"**. Seu perfil será atualizado imediatamente na sessão.  
2. **Como trocar o banco H2 para PostgreSQL ou MySQL?** Edite o arquivo `src/main/resources/application.properties` descomentando as propriedades de conexão do PostgreSQL/MySQL e adicione o driver correspondente no `pom.xml`.  
3. **Suporte e Equipe Responsável:** Equipe de Gerência: Erina Ami Sakakida e Vitor Hideaki Tsunoda • Disciplina de Engenharia de Software (Prof. Rogério) \- FCT UNESP Presidente Prudente.