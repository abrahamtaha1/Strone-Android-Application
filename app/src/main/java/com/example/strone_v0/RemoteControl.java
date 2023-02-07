package com.example.strone_v0;

public class RemoteControl {

        private final static byte Abe_Command_on = 0x1;
        private final static byte Abe_Command_off = 0x2;

        private final static byte Alarm_Command_on = 0x3;
        private final static byte Alarm_Command_off = 0x4;

        private final static byte Jor_Command_on = 0x5;
        private final static byte Jor_Command_off = 0x6;

        private final static byte VALUE_OFF = 0x0;
        private final static byte VALUE_ON = (byte)0xFF;

        private com.example.strone_v0.BLEController bleController;

        public RemoteControl(com.example.strone_v0.BLEController bleController) {
            this.bleController = bleController;
        }

        private byte [] createControlWord(byte type, byte ... args) {
            byte [] command = new byte[1];
            if( args[0] == VALUE_ON){
                command[0] = type;
            }else if (args[0] == VALUE_OFF && type == Abe_Command_on )
                command[0] = Abe_Command_off;
             else if (args[0] == VALUE_OFF && type == Jor_Command_on )
                command[0] = Jor_Command_off;
             else if (args[0] == VALUE_OFF && type == Alarm_Command_on )
                command[0] = Alarm_Command_off;

            return command;
        }

        public void Abe_Switch(boolean on) {
            this.bleController.sendData(createControlWord(Abe_Command_on, on?VALUE_ON:VALUE_OFF));
        }

        public void Jor_Switch(boolean on) {
            this.bleController.sendData(createControlWord(Jor_Command_on, on?VALUE_ON:VALUE_OFF));
        }


        public void Alarm_Switch(boolean on) {
            this.bleController.sendData(createControlWord(Alarm_Command_on, on?VALUE_ON:VALUE_OFF));
        }

        public void switchLED(boolean on) {
            this.bleController.sendData(createControlWord(Abe_Command_on, on?VALUE_ON:VALUE_OFF));
        }

    }

