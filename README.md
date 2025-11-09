Java 소켓 계산기
Java의 소켓(Socket) 프로그래밍과 스레드 풀(Thread Pool)을 활용한 멀티클라이언트 지원 사칙연산 계산기 프로젝트이다.

사용방법
**1. 설정 파일 (server_info.dat)**
클라이언트가 접속할 서버의 IP와 포트 번호를 server_info.dat 파일로 관리한다. CalculatorClient.java와 같은 위치에 server_info.dat 파일을 생성한다.

파일 내용 예시:

127.0.0.1
5678
만약 이 파일이 존재하지 않으면, 클라이언트는 기본값(localhost:5678)으로 서버에 접속을 시도한다.

**2. 서버 실행**
새 터미널을 열고 서버를 실행한다.

java CalculatorServer
Server Started: 5678 메시지가 출력되면 정상적으로 대기 중인 상태이다.

**3. 클라이언트 실행**
별도의 새 터미널을 열고 클라이언트를 실행한다.

java CalculatorClient
서버에 연결되면 Please enter operation you want: 메시지가 출력된다.

**4. 명령어 입력**
정해진 양식(연산자 숫자1 숫자2)에 맞춰 명령어를 입력한다.

입력: ADD 10 20

출력: Server Response: 10.0 + 20.0 = 30.0

입력: DIV 10 0

출력: Server Response: Error: Division by Zero

주요 구현 내용
**1. CalculatorServer (서버 메인)**
스레드 풀 (ThreadPool): ExecutorService를 newFixedThreadPool(10)로 생성하여, 최대 10개의 클라이언트를 동시에 처리할 수 있도록 구현했다.

클라이언트 연결: while(true) 루프 내에서 welcomeSocket.accept()를 통해 클라이언트의 연결을 지속적으로 대기한다.

작업 위임: 클라이언트 Socket이 연결되면, 실제 처리를 담당할 Calculatorthread의 인스턴스를 생성하여 스레드 풀에 submit한다. 메인 스레드는 즉시 다음 클라이언트 연결을 받으러 간다.

**2. Calculatorthread (스레드 및 연산)**
Runnable 구현: 스레드 풀에서 작업을 처리할 수 있도록 Runnable 인터페이스를 구현했다.

I/O 처리: try-with-resources 구문을 사용하여 Socket의 InputStream과 OutputStream을 자동으로 닫도록 처리했다.

명령어 파싱: 클라이언트가 보낸 메시지(clientSentence)를 StringTokenizer를 이용해 연산자(Operator)와 피연산자(Operand)로 분리한다.

연산 처리 (R1): if-else if 문을 통해 ADD, SUB, MUL, DIV 연산을 구분하여 수행한다.

오류 처리 (R2):

DIV 연산 시 n2 == 0인지 검사하여 "Division by Zero" 오류를 클라이언트에게 전송한다.

st.hasMoreTokens()를 검사하여 피연산자가 2개보다 많이 들어온 경우 "Too Many Numbers" 오류를 전송한다.

catch(NoSuchElementException): 피연산자가 부족할 경우("ADD 10") 발생하는 예외를 감지하여 서버 콘솔에 "Invalid Input"을 출력한다.

else 블록: ADD~DIV 외의 연산자가 들어오면 "Invalid Operation" 오류를 전송한다.

**3. CalculatorClient (클라이언트)**
설정 파일 (R4): BufferedReader와 FileReader를 사용해 server_info.dat 파일을 먼저 읽는다.

파일 예외 처리: try-catch 문을 사용하여 FileNotFoundException이 발생하면(파일이 없으면) 기본 IP와 포트(localhost:5678)를 사용하도록 설정했다.

서버 통신: System.in으로 사용자 입력을 받아 writeBytes로 서버에 전송하고, readLine으로 서버의 응답을 받아 콘솔에 출력한다.
