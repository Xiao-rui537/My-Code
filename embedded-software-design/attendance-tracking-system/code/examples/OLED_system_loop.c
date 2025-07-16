#include "test.h"
#include "OLED_0in96.h"
#include <sys/time.h>
#include <time.h>
#include <unistd.h>
#include <stdlib.h>

PAINT_TIME sPaint_time;

typedef struct CPU_PACKED         //定义一个cpu occupy的结构体
{
char name[20];             //定义一个char类型的数组名name有20个元素
unsigned int user;        //定义一个无符号的int类型的user
unsigned int nice;        //定义一个无符号的int类型的nice
unsigned int system;    //定义一个无符号的int类型的system
unsigned int idle;         //定义一个无符号的int类型的idle
unsigned int iowait;
unsigned int irq;
unsigned int softirq;
}CPU_OCCUPY;


double cal_cpuoccupy (CPU_OCCUPY *o, CPU_OCCUPY *n)
{
    double od, nd;
    double id, sd;
    double cpu_use ;
 
    od = (double) (o->user + o->nice + o->system +o->idle+o->softirq+o->iowait+o->irq);//第一次(用户+优先级+系统+空闲)的时间再赋给od
    nd = (double) (n->user + n->nice + n->system +n->idle+n->softirq+n->iowait+n->irq);//第二次(用户+优先级+系统+空闲)的时间再赋给od
 
    id = (double) (n->idle);    //用户第一次和第二次的时间之差再赋给id
    sd = (double) (o->idle) ;    //系统第一次和第二次的时间之差再赋给sd
    if((nd-od) != 0)
    cpu_use =100.0- ((id-sd))/(nd-od)*100.00; //((用户+系统)乖100)除(第一次和第二次的时间差)再赋给g_cpu_used
    else cpu_use = 0;
    return cpu_use;
}
 
void get_cpuoccupy (CPU_OCCUPY *cpust)
{
    FILE *fd;
    char buff[256];
    CPU_OCCUPY *cpu_occupy;
    cpu_occupy=cpust;
 
    fd = fopen ("/proc/stat", "r");
    fgets (buff, sizeof(buff), fd);
 
    sscanf (buff, "%s %u %u %u %u %u %u %u", cpu_occupy->name, &cpu_occupy->user, &cpu_occupy->nice,&cpu_occupy->system, &cpu_occupy->idle ,&cpu_occupy->iowait,&cpu_occupy->irq,&cpu_occupy->softirq);
 
    fclose(fd);
}
 
double getCpuRate()
{
    CPU_OCCUPY cpu_stat1;
    CPU_OCCUPY cpu_stat2;
    double cpu;
    get_cpuoccupy((CPU_OCCUPY *)&cpu_stat1);
    sleep(1);
 
    //第二次获取cpu使用情况
    get_cpuoccupy((CPU_OCCUPY *)&cpu_stat2);
 
    //计算cpu使用率
    cpu = cal_cpuoccupy ((CPU_OCCUPY *)&cpu_stat1, (CPU_OCCUPY *)&cpu_stat2);
 
    return cpu;
}

int OLED_system_loop(void){

    time_t timep;
    struct tm *p;
    double cpu_capqulry;
    
    char day_for_year[10] = {0};

    if(DEV_ModuleInit() != 0) {
        printf("dev Init  Faild \n");
        return -1;
    }
    OLED_0in96_Init();
    DEV_Delay_ms(500);
    Paint_Clear(BLACK);
    UBYTE *BlackImage;
    UWORD Imagesize = ((OLED_0in96_WIDTH%8==0)? (OLED_0in96_WIDTH/8): (OLED_0in96_WIDTH/8+1)) * OLED_0in96_HEIGHT;
    if((BlackImage = (UBYTE *)malloc(Imagesize)) == NULL) {
            printf("Failed to apply for black memory...\r\n");
            return -1;
    }
    printf("Paint_NewImage\r\n");
    Paint_NewImage(BlackImage, OLED_0in96_WIDTH, OLED_0in96_HEIGHT, 90, BLACK);  

    printf("Drawing\r\n");
    //1.Select Image
    Paint_SelectImage(BlackImage);

    while(1){
        time (&timep);
        p=gmtime(&timep);
        
        sPaint_time.Year=1900+p->tm_year;
        sPaint_time.Month=1+p->tm_mon;
        sPaint_time.Day=p->tm_mday;

        sprintf(day_for_year,"%d-%d-%d",1900+p->tm_year,1+p->tm_mon,p->tm_mday);

        sPaint_time.Hour=8+p->tm_hour;
        sPaint_time.Min=p->tm_min;
        sPaint_time.Sec=p->tm_sec;

       
       
        //printf("%d\n",p->tm_yday); /*从今年1月1日算起至今的天数，范围为0-365*/
 

        cpu_capqulry = getCpuRate();
        
        Paint_Clear(BLACK);

        Paint_DrawString_EN(10,0, day_for_year , &Font16, WHITE, WHITE);
        Paint_DrawTime(20,16,&sPaint_time,&Font16,WHITE,WHITE);
        Paint_DrawNum(40,32,cpu_capqulry,&Font16,1,WHITE,WHITE);
        Paint_DrawChar(72,32,'%',&Font16,WHITE,WHITE);
        OLED_0in96_display(BlackImage);
        DEV_Delay_ms(1000); 
    }
}