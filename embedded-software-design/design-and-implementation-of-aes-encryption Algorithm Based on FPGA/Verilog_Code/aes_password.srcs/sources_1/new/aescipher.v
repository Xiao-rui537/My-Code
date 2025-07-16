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
//aescipher的模块声明(一次AES加密算法要进行的步骤)
module aescipher(
    clk,
    rst,
    datain,
    key,
    dataout
    );
    
    //clk时钟信号
    input clk;
    input rst;
    //datain输入的待加密的原始数据
    input [127:0] datain;
    //key用于加密的密钥
    input [127:0] key;
    //dataout加密后的加密数据
    output[127:0] dataout;
    
    //用于储存每一轮加密后的数据
    wire [127:0] r0_out,r1_out,r2_out,r3_out,r4_out,r5_out,r6_out,r7_out,r8_out,r9_out;
    //用于储存每一轮使用的密钥
    wire [127:0] keyout1,keyout2,keyout3,keyout4,keyout5,keyout6,keyout7,keyout8,keyout9;
	 
	//第一轮加密操作，使用异或操作将输入数据datain与密钥key进行组合，结果存储在r0_out中(第一次AddRoundKey)
	assign r0_out = datain^key;
	
	//round用于每个轮的加密（一共需要10轮的加密） 其中每一轮加密的结果都作为下一轮加密的原始数据，并且密钥也根据每一轮产生的作为下一轮的密钥
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
    
    //这里的roundlast是最后一次加密，因为根据算法，最后一轮不需要进行mixcolumn操作
    roundlast r10(
    .clk(clk),
    .rst(rst),
    .rc(4'b1001),
    .rin(r9_out),
    .keylastin(keyout9),
    .fout(dataout)
    );
    
endmodule
