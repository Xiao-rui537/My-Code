#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>
#include <errno.h>
#include "gpio.h"

#define SYSFS_GPIO_DIR "/sys/class/gpio"
#define MAX_BUF 64

/****************************************************************
 * gpio_export
 * 导出 GPIO 引脚，使其可用于操作
 ****************************************************************/
int gpio_export(unsigned int gpio)
{
    int fd, len;
    char buf[MAX_BUF];

    // 打开 /sys/class/gpio/export 文件
    fd = open(SYSFS_GPIO_DIR "/export", O_WRONLY);
    if (fd < 0) {
        perror("gpio/export");
        return fd;
    }

    // 将 GPIO 引脚编号写入文件以进行导出
    len = snprintf(buf, sizeof(buf), "%d", gpio);
    write(fd, buf, len);
    close(fd);

    return 0;
}

/****************************************************************
 * gpio_unexport
 * 取消导出 GPIO 引脚
 ****************************************************************/
int gpio_unexport(unsigned int gpio)
{
    int fd, len;
    char buf[MAX_BUF];

    // 打开 /sys/class/gpio/unexport 文件
    fd = open(SYSFS_GPIO_DIR "/unexport", O_WRONLY);
    if (fd < 0) {
        perror("gpio/unexport");
        return fd;
    }

    // 将 GPIO 引脚编号写入文件以取消导出
    len = snprintf(buf, sizeof(buf), "%d", gpio);
    write(fd, buf, len);
    close(fd);
    
    return 0;
}

/****************************************************************
 * gpio_set_dir
 * 设置 GPIO 引脚的方向（输入或输出）
 ****************************************************************/
int gpio_set_dir(unsigned int gpio, unsigned int out_flag)
{
    int fd, len;
    char buf[MAX_BUF];

    // 生成 GPIO 引脚的方向文件路径
    len = snprintf(buf, sizeof(buf), SYSFS_GPIO_DIR "/gpio%d/direction", gpio);

    // 打开方向文件
    fd = open(buf, O_WRONLY);
    if (fd < 0) {
        perror("gpio/direction");
        return fd;
    }

    // 如果 out_flag 为 1，设置为输出；否则设置为输入
    if (out_flag)
        write(fd, "out", 3);
    else
        write(fd, "in", 2);

    close(fd);
    return 0;
}

/****************************************************************
 * gpio_set_value
 * 设置 GPIO 引脚的值（高电平或低电平）
 ****************************************************************/
int gpio_set_value(unsigned int gpio, unsigned int value)
{
    int fd, len;
    char buf[MAX_BUF];

    // 生成 GPIO 引脚的值文件路径
    len = snprintf(buf, sizeof(buf), SYSFS_GPIO_DIR "/gpio%d/value", gpio);

    // 打开值文件
    fd = open(buf, O_WRONLY);
    if (fd < 0) {
        perror("gpio/set-value");
        return fd;
    }

    // 根据 value 值写入 "1" 或 "0"
    if (value)
        write(fd, "1", 1);
    else
        write(fd, "0", 1);

    close(fd);
    return 0;
}

/****************************************************************
 * gpio_get_value
 * 获取 GPIO 引脚的值（读取输入信号的电平）
 ****************************************************************/
int gpio_get_value(unsigned int gpio, unsigned int* value)
{
    int fd, len;
    char buf[MAX_BUF];
    char ch;

    // 生成 GPIO 引脚的值文件路径
    len = snprintf(buf, sizeof(buf), SYSFS_GPIO_DIR "/gpio%d/value", gpio);

    // 打开值文件
    fd = open(buf, O_RDONLY);
    if (fd < 0) {
        perror("gpio/get-value");
        return fd;
    }

    // 读取文件中的值（"0" 或 "1"）
    read(fd, &ch, 1);

    // 如果值为 "1"，则设置 *value 为 1，否则为 0
    if (ch != '0') {
        *value = 1;
    } else {
        *value = 0;
    }

    close(fd);
    return 0;
}