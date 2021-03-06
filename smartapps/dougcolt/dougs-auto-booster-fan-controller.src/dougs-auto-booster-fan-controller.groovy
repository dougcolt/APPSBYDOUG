/**
 *  Auto Booster Fan
 *
 *  Copyright 2020 Doug Colt
 */
 
definition(
    name: "DOUG'S AUTO BOOSTER FAN CONTROLLER",
    namespace: "dougcolt",
    author: "Doug Colt",
    description: "Turns on the blower fan automatically when thermostat switches into heating mode. ",
    category: "My Apps",
    iconUrl: "https://cdn.device-icons.smartthings.com/Entertainment/entertainment15-icn@2x.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	section("ChooseThermostat:") {
        input "thethermostat", "capability.thermostatOperatingState", required: true, title: "Select Thermostat to be Monitored:"
    }
    section("Select Blower Fan:") {
        input "theswitch", "capability.switch", required: true, title: "Select the Booster Fan:"
    }
    
}

def installed() {
	log.debug "Installed with settings: ${settings}"
 	subscribe(thethermostat, "thermostatOperatingState.heating", heatingDetectedHandler)
	subscribe(thethermostat, "thermostatOperatingState.idle", idleDetectedHandler)
}

def updated() {
	log.debug "Updated with settings: ${settings}"
	unsubscribe()
    subscribe(thethermostat, "thermostatOperatingState.heating", heatingDetectedHandler)
	subscribe(thethermostat, "thermostatOperatingState.idle", idleDetectedHandler)
}


def heatingDetectedHandler(evt) {
    log.debug "Doug's heatingDetectedHandler called: $evt"
    def thedelay= 60 * 1
     runIn(thedelay,turnOnSwitch)
     }
      

def idleDetectedHandler(evt) {
    log.debug "Doug's idleDetectedHandler called: $evt"
     def thedelay= 60 * 2
     runIn(thedelay,turnOffSwitch)
     }
     
def turnOnSwitch() {
	log.debug "Turning on booster fans."
    theswitch.on()
}

def turnOffSwitch() {
	log.debug "Turning off booster fans."
    theswitch.off()
}

