package rasp;

import java.io.IOException;

import com.pi4j.io.gpio.*;

public class Controle {
	
	final GpioController gpio = GpioFactory.getInstance();
	String[] comandos;

//	int enDirPin = 26;
//	int enEsqPin = 23;
//	int frenteDirPin = 31;			//IN1
//	int trasDirPin = 11;			//IN2
//	int frenteEsqPin = 10;			//IN3
//	int trasEsqPin = 6;			//IN4
//	int ledPin = 3;
//	int servoPin = 1;
	
	
	GpioPinPwmOutput enaDir = gpio.provisionPwmOutputPin(RaspiPin.GPIO_26,0);
	GpioPinPwmOutput enaEsq = gpio.provisionPwmOutputPin(RaspiPin.GPIO_23,0);
	GpioPinDigitalOutput frenteDir = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_31,PinState.LOW);
	GpioPinDigitalOutput trasDir = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_11,PinState.LOW);
	GpioPinDigitalOutput frenteEsq = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_10,PinState.LOW);
	GpioPinDigitalOutput trasEsq = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06,PinState.LOW);
	GpioPinDigitalOutput ledIR = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03,PinState.LOW);
	
		
	
	/*
	comandos[0] = sinal de controle ("ctrl")
	comandos[1] = direcao de movimento
	comandos[2] = potencia do movimento (pwm de 0-100%)
	comandos[3] = angulo do servo da camera (0-90)
	*/
	
	
	
	public Controle() throws InterruptedException {
		
		com.pi4j.wiringpi.Gpio.wiringPiSetup();
		
//		SoftPwm.softPwmCreate(enDirPin, 0, 100);
//		SoftPwm.softPwmCreate(enEsqPin, 0, 100);
		
//		frenteDir.setShutdownOptions(false, PinState.LOW);
//		trasDir.setShutdownOptions(false, PinState.LOW);
//		frenteEsq.setShutdownOptions(false, PinState.LOW);
//		trasEsq.setShutdownOptions(false, PinState.LOW);
//		ledIR.setShutdownOptions(false, PinState.LOW);

//		gpio.unprovisionPin(frenteDir);
//		gpio.unprovisionPin(trasDir);
//		gpio.unprovisionPin(frenteEsq);
//		gpio.unprovisionPin(trasEsq);
//		gpio.unprovisionPin(ledIR);
//		gpio.unprovisionPin(servo);
				
				
	}
	
	public void direcao(String direcao, String pot) throws InterruptedException {
//		System.out.println("Comando de direção recebido: " + direcao);
		int potencia = Integer.parseInt(pot);
		switch(direcao) {
		case "Frente":
//			SoftPwm.softPwmWrite(enDirPin, potencia);
//			SoftPwm.softPwmWrite(enEsqPin, potencia);
			enaDir.setPwm((int) (potencia*1024));
			enaEsq.setPwm((int) (potencia*1024));
			frenteDir.high();
			frenteEsq.high();
			trasDir.low();
			trasEsq.low();
			break;
			
		case "FrenteDireita":
//			SoftPwm.softPwmWrite(enDirPin, (int) (potencia*0.5));
//			SoftPwm.softPwmWrite(enEsqPin, potencia);
			enaDir.setPwm((int) (potencia*512));
			enaEsq.setPwm((int) (potencia*1024));
			frenteDir.low();
			frenteEsq.high();
			trasDir.low();
			trasEsq.low();
			break;
			
		case "Direita":
//			SoftPwm.softPwmWrite(enDirPin, potencia);
//			SoftPwm.softPwmWrite(enEsqPin, potencia);
			enaDir.setPwm((int) (potencia*1024));
			enaEsq.setPwm((int) (potencia*1024));
			frenteDir.low();
			frenteEsq.high();
			trasDir.high();
			trasEsq.low();
			break;
			
		case "AtrásDireita":
//			SoftPwm.softPwmWrite(enDirPin, (int) (potencia*0.5));
//			SoftPwm.softPwmWrite(enEsqPin, potencia);
			enaDir.setPwm((int) (potencia*512));
			enaEsq.setPwm((int) (potencia*1024));
			frenteDir.low();
			frenteEsq.low();
			trasDir.low();
			trasEsq.high();
			break;
			
		case "Atrás":
//			SoftPwm.softPwmWrite(enDirPin, potencia);
//			SoftPwm.softPwmWrite(enEsqPin, potencia);
			enaDir.setPwm((int) (potencia*1024));
			enaEsq.setPwm((int) (potencia*1024));
			frenteDir.low();
			frenteEsq.low();
			trasDir.high();
			trasEsq.high();
			break;
			
		case "AtrásEsquerda":
//			SoftPwm.softPwmWrite(enDirPin, potencia);
//			SoftPwm.softPwmWrite(enEsqPin, (int) (potencia*0.5));
			enaDir.setPwm((int) (potencia*1024));
			enaEsq.setPwm((int) (potencia*512));
			frenteDir.low();
			frenteEsq.low();
			trasDir.high();
			trasEsq.low();
			break;
			
		case "Esquerda":
//			SoftPwm.softPwmWrite(enDirPin, potencia);
//			SoftPwm.softPwmWrite(enEsqPin, potencia);
			enaDir.setPwm((int) (potencia*512));
			enaEsq.setPwm((int) (potencia*1024));
			frenteDir.high();
			frenteEsq.low();
			trasDir.low();
			trasEsq.high();
			break;
			
		case "FrenteEsquerda":
//			SoftPwm.softPwmWrite(enDirPin, potencia);
//			SoftPwm.softPwmWrite(enEsqPin, (int) (potencia*0.5));
			enaDir.setPwm((int) (potencia*1024));
			enaEsq.setPwm((int) (potencia*512));
			frenteDir.high();
			frenteEsq.low();
			trasDir.low();
			trasEsq.low();
			break;
			
		case "Parado":
//			SoftPwm.softPwmWrite(enDirPin, potencia);
//			SoftPwm.softPwmWrite(enEsqPin, potencia);
			enaDir.setPwm((int) (potencia*512));
			enaEsq.setPwm((int) (potencia*1024));
			frenteDir.high();
			frenteEsq.high();
			trasDir.high();
			trasEsq.high();
			Thread.sleep(50);
			
//			SoftPwm.softPwmWrite(enDirPin, 0);
//			SoftPwm.softPwmWrite(enEsqPin, 0);
			enaDir.setPwm((int) (potencia*512));
			enaEsq.setPwm((int) (potencia*1024));
			frenteDir.low();
			frenteEsq.low();
			trasDir.low();
			trasEsq.low();
			break;
			
		default:
//			SoftPwm.softPwmWrite(enDirPin, 0);
//			SoftPwm.softPwmWrite(enEsqPin, 0);
			enaDir.setPwm((int) (potencia*512));
			enaEsq.setPwm((int) (potencia*1024));
			frenteDir.low();
			frenteEsq.low();
			trasDir.low();
			trasEsq.low();
			break;
		}
	
	gpio.shutdown();
	
//	SoftPwm.softPwmStop(enDirPin);
//	SoftPwm.softPwmStop(enEsqPin);
		
	}
	
	
	public void angulo(int angulo) {
//		System.out.println("Comando de ângulo recebido: " + angulo);
		Runtime cmd = Runtime.getRuntime();
		Double controle = angulo*0.056+2.5;
		try {
//			cmd.exec("cd /home/pi/Desktop");
			cmd.exec("python controleServo.py " + String.valueOf(controle));
//			System.out.println("Comando de ângulo executado: " + controle);
//			cmd.exec(String.valueOf(controle)+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
