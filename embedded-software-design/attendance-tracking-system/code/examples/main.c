#include <stdlib.h>
#include <signal.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include "gpio.h"
#include "test.h"

#define GPIO_IN 65  
#define GPIO_OUT 35   

void Handler(int signo)
{
    printf("\r\nHandler:exit\r\n");
    DEV_ModuleExit();
    exit(0);
}

int main(int argc, char* argv[])
{
    unsigned int gpio = GPIO_IN;  
    unsigned int power_gpio = GPIO_OUT;  
    int value;
    int power_value = 0;

    gpio_export(gpio);
    gpio_set_dir(gpio, 0);  
    gpio_export(power_gpio);
    gpio_set_dir(power_gpio, 1);  

    signal(SIGINT, Handler);

    if (argc != 2) {
        printf("please input open to Start! \r\n");
        exit(1);
    }

    printf("%s OLED Moudule\r\n", argv[1]);

    if (strcmp(argv[1], "open") == 0){
        while (1) {
            gpio_get_value(gpio, &value);
            if (value) {
                printf("scan the body !! the value is %d \n", value);
                power_value = 1;  
                gpio_set_value(power_gpio, power_value);
                OLED_0in91_test();
                power_value = 0;
                gpio_set_value(power_gpio, power_value);
            }
            else {
                printf("No scan the body !! the value is %d \n", value);
            }
            sleep(1);
        }
}
    else if (strcmp(argv[1], "0.95rgb") == 0)
        OLED_0in95_rgb_test();
    else if (strcmp(argv[1], "0.96") == 0)
        OLED_0in96_test();
    else if (strcmp(argv[1], "1.3") == 0)
        OLED_1in3_test();
    else if (strcmp(argv[1], "1.3c") == 0)
        OLED_1in3_c_test();
    else if (strcmp(argv[1], "1.5") == 0)
        OLED_1in5_test();
    else if (strcmp(argv[1], "1.5rgb") == 0)
        OLED_1in5_rgb_test();
    else if (strcmp(argv[1], "sys") == 0)
        OLED_system_loop();
    else
        printf("error: can not find the OLED\r\n");

    return 0;
}

