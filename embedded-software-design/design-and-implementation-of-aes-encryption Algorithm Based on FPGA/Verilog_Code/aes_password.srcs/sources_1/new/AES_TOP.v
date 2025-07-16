`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/10/26 19:47:44
// Design Name: 
// Module Name: test
// Project Name: 
// Target Devices: 
// Tool Versions: 
// Description: 
// 
// Dependencies: 
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
//////////////////////////////////////////////////////////////////////////////////
//���Ƕ����ļ�
module AES_TOP(
    clk,
    rst,
    datain,
    key,
    tempout
    );
    
    // ����ʱ���ź�
    input clk;
    input rst;
    // �������ǰ���źź���Կ
    output [127:0] datain;
    output [127:0] key;
    
    //���ڴ��沢����Ӽ��ܺ������
    output [127:0] tempout;

    assign datain = 128'h3243f6a8885a308d313198a2e0370734;
    assign key = 128'h2b7e151628aed2a6abf7158809cf4f3c;

    //ʵ����aescipherģ�飬��ģ���������ʹ�������datainԭʼ���ݣ�����key��Կ��AES���ܣ����ͨ��dataout������ܺ������
    aescipher uut1(
    .clk(clk),
    .rst(rst),
    .datain(datain),
    .key(key),
    .dataout(tempout)
    );

endmodule
