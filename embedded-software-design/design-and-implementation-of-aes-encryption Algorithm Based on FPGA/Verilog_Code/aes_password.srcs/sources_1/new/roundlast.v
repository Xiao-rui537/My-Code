`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/10/26 20:26:04
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
//roundlast的模块声明（最后一轮加密算法要做的步骤）
module roundlast(
    clk,
    rc,
    rst,
    rin,
    keylastin,
    fout
    );
    
    //时钟信号
    input clk;
    input rst;
    //储存轮常数（进行到第几轮了）
    input [3:0]rc;
    //最后一轮用于加密的原始数据
    input [127:0]rin;
    //最后一轮用于加密的密钥
    input [127:0]keylastin;
    //最后一轮加密后输出的加密数据
    output reg [127:0]fout;

    //用于储存SubBytes（S盒替换）、ShiftRows（行移位）、MixColumns（列混淆）后的输出
    wire [127:0] sb,sr,mcl,keyout;
    wire [127:0]  fout_comb;

    //生成最后一轮的密钥
    keygeneration t0(
        rc,
        keylastin,
        keyout
        );
    
    //进行SubBytes（S盒替换）    
    subbytes t1(
        rin,
        sb
        );
    
    //进行ShiftRows（行移位）   
    shiftrow t2(
        sb,
        sr
        );
    //注意：最后一轮不需要进行MixColumns（列混淆）
    
    //进行最后一次AddRoundKey（轮密钥加），得到加密的最终结果   
    assign fout_comb= keyout^sr;
    
    always@(posedge clk or posedge rst)begin
        if(rst)begin
            fout <= 0;
        end
        else begin
            fout <= fout_comb;
        end
    end

endmodule
