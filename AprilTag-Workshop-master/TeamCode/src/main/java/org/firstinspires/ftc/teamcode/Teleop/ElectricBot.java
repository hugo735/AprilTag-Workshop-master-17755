package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Teleop ElectricBot", group="Linear Opmode")
public class ElectricBot extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor rightDrive = null;private DcMotor leftDrive = null;

    private DcMotor linearM = null;

    private Servo intake1 = null;
    private Servo intake2 = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftDrive  = hardwareMap.get(DcMotor.class, "leftD");
        rightDrive = hardwareMap.get(DcMotor.class, "rightD");

        linearM = hardwareMap.get(DcMotor.class, "linearM");

        intake1 = hardwareMap.get(Servo.class, "s1");
        intake2 = hardwareMap.get(Servo.class, "s2");

        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        linearM.setDirection(DcMotorSimple.Direction.FORWARD);

        leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        linearM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {

            double leftPower;
            double rightPower;

            double drive = -gamepad1.left_stick_y;
            double turn  =  gamepad1.right_stick_x;
            leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
            rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;

            leftDrive.setPower(leftPower * 0.9);
            rightDrive.setPower(rightPower * 0.9);

            if (gamepad1.right_bumper){
                intake1.setPosition(0.7);
                intake2.setPosition(-.5);
            } else if (gamepad1.left_bumper) {
                //intake 1 es la derecha
                intake1.setPosition(0.48);
                intake2.setPosition(0.325);
            }

            if (gamepad2.a) {
                linearM.setPower(-1);
            } else if (gamepad2.y) {
                linearM.setPower(.8);
            } else {
                linearM.setPower(0);
            }

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();
        }
    }


}

