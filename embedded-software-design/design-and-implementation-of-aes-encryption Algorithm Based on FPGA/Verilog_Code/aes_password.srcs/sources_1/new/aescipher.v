`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/10/26 19:55:20
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
//aescipher��ģ������(һ��AES�����㷨Ҫ���еĲ���)
module aescipher(
    clk,
    rst,
    datain,
    key,
    dataout
    );
    
    //clkʱ���ź�
    input clk;
    input rst;
    //datain����Ĵ����ܵ�ԭʼ����
    input [127:0] datain;
    //key���ڼ��ܵ���Կ
    input [127:0] key;
    //dataout���ܺ�ļ�������
    output[127:0] dataout;
    
    //���ڴ���ÿһ�ּ��ܺ������
    wire [127:0] r0_out,r1_out,r2_out,r3_out,r4_out,r5_out,r6_out,r7_out,r8_out,r9_out;
    //���ڴ���ÿһ��ʹ�õ���Կ
    wire [127:0] keyout1,keyout2,keyout3,keyout4,keyout5,keyout6,keyout7,keyout8,keyout9;
	 
	//��һ�ּ��ܲ�����ʹ������������������datain����Կkey������ϣ�����洢��r0_out��(��һ��AddRoundKey)
	assign r0_out = datain^key;
	
	//round����ÿ���ֵļ��ܣ�һ����Ҫ10�ֵļ��ܣ� ����ÿһ�ּ��ܵĽ������Ϊ��һ�ּ��ܵ�ԭʼ���ݣ�������ԿҲ����ÿһ�ֲ�������Ϊ��һ�ֵ���Կ
    rounds r1(
    .clk(clk),
    .rst(rst),
    .rc(4'b0000),
    .data(r0_out),
    .keyin(key),
    .keyout(keyout1),
    .rndout(r1_out)
    );
    
    rounds r2(
    .clk(clk),
    .rst(rst),
    .rc(4'b0001),
    .data(r1_out),
    .keyin(keyout1),
    .keyout(keyout2),
    .rndout(r2_out)
    );
    
    rounds r3(
    .clk(clk),
    .rst(rst),
    .rc(4'b0010),
    .data(r2_out),
    .keyin(keyout2),
    .keyout(keyout3),
    .rndout(r3_out)
    );
    
    rounds r4(
    .clk(clk),
    .rst(rst),
    .rc(4'b0011),
    .data(r3_out),
    .keyin(keyout3),
    .keyout(keyout4),
    .rndout(r4_out)
    );
    
    rounds r5(
    .clk(clk),
    .rst(rst),
    .rc(4'b0100),
    .data(r4_out),
    .keyin(keyout4),
    .keyout(keyout5),
    .rndout(r5_out)
    );
    
    rounds r6(
    .clk(clk),
    .rst(rst),
    .rc(4'b0101),
    .data(r5_out),
    .keyin(keyout5),
    .keyout(keyout6),
    .rndout(r6_out)
    );
    
    rounds r7(
    .clk(clk),
    .rst(rst),
    .rc(4'b0110),
    .data(r6_out),
    .keyin(keyout6),
    .keyout(keyout7),
    .rndout(r7_out)
    );
    
    rounds r8(
    .clk(clk),
    .rst(rst),
    .rc(4'b0111),
    .data(r7_out),
    .keyin(keyout7),
    .keyout(keyout8),
    .rndout(r8_out)
    );
    
    rounds r9(
    .clk(clk),
    .rst(rst),
    .rc(4'b1000),
    .data(r8_out),
    .keyin(keyout8),
    .keyout(keyout9),
    .rndout(r9_out)
    );
    
    //�����roundlast�����һ�μ��ܣ���Ϊ�����㷨�����һ�ֲ���Ҫ����mixcolumn����
    roundlast r10(
    .clk(clk),
    .rst(rst),
    .rc(4'b1001),
    .rin(r9_out),
    .keylastin(keyout9),
    .fout(dataout)
    );
    
endmodule
