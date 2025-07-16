/*****************************************************************************
* | File      	:   OLED_0in91_test.c
* | Author      :   Waveshare team
* | Function    :   0.91inch OLED Module test demo
* | Info        :
*----------------
* |	This version:   V2.0
* | Date        :   2020-08-17
* | Info        :
* -----------------------------------------------------------------------------
#
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documnetation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to  whom the Software is
# furished to do so, subject to the following conditions:
#
# The above copyright notice and this permission notice shall be included in
# all copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS OR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
# THE SOFTWARE.
#
******************************************************************************/
#include "test.h"
#include "OLED_0in91.h"

int OLED_0in91_test(void)
{
    if (DEV_ModuleInit() != 0) {
        return -1;  // ��ʼ��ʧ��
    }

    if (USE_SPI) {
        return -1;  // ʹ��SPIʱ�����ش���
    }

    OLED_0in91_Init();
    DEV_Delay_ms(500);
    OLED_0in91_Clear();

    // 0.Create a new image cache
    UBYTE* BlackImage = NULL;  // ��ʼ��ָ��ΪNULL
    UWORD Imagesize = ((OLED_0in91_WIDTH % 8 == 0) ? (OLED_0in91_WIDTH / 8) : (OLED_0in91_WIDTH / 8 + 1)) * OLED_0in91_HEIGHT;

    // �����ڴ沢���
    BlackImage = (UBYTE*)malloc(Imagesize);
    if (BlackImage == NULL) {
        return -1;  // �ڴ����ʧ��
    }

    // ������ͼ��
    Paint_NewImage(BlackImage, OLED_0in91_HEIGHT, OLED_0in91_WIDTH, 90, BLACK);

    // 1.Select Image
    Paint_SelectImage(BlackImage);
    DEV_Delay_ms(500);

    // ��ջ���
    Paint_Clear(BLACK);

    // ��������
    Paint_DrawString_EN(20, 5, "Clock In", &Font12, WHITE, WHITE);
    Paint_DrawString_EN(20, 15, "Successfully", &Font12, WHITE, WHITE);
    // ��ʾͼ��
    OLED_0in91_Display(BlackImage);

    // ��ʱ 2 ��
    DEV_Delay_ms(5000);

    // ��ջ���
    Paint_Clear(BLACK);
    OLED_0in91_Display(BlackImage);
    // �ͷ��ڴ�
    free(BlackImage);

    return 0;  // �ɹ����
}


