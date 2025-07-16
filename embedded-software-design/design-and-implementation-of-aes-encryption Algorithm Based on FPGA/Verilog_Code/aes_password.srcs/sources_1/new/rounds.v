`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/10/26 20:03:12
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
//rounds的模块声明（每一轮加密算法要做的步骤）{一共需要10轮} 每轮包括以下四个主要步骤：SubBytes（S盒替换）、ShiftRows（行移位）、MixColumns（列混淆）、AddRoundKey（轮密钥加）
module rounds(
    clk,
    rst,
    rc,
    data,
    keyin,
    keyout,
    rndout
    );
    
    //时钟信号
    input clk;
    //复位信号
    input rst;
    //储存轮常数（进行到第几轮了）
    input [3:0]rc;
    //本轮用于加密的原始数据
    input [127:0]data;
    //本轮用于加密的密钥
    input [127:0]keyin;
    //轮密钥加后的密钥，用于下一轮的加密密钥
    output [127:0]keyout;
    //本轮加密后的加密数据
    output reg [127:0]rndout = 0;

    //用于储存SubBytes（S盒替换）、ShiftRows（行移位）、MixColumns（列混淆）后的输出
    wire [127:0] sb,sr,mcl,rndout_comb;

    //进行密钥拓展
    keygeneration t0
    (
    rc,
    keyin,
    keyout
    );
    
    //进行SubBytes（S盒替换）
    subbytes t1
    (
    data,
    sb
     );
    
    //进行ShiftRows（行移位）
    shiftrow t2
    (
    sb,
    sr
    );
    
    //进行MixColumns（列混淆）
    mixcolumn t3
    (
    sr,
    mcl
    );
      
    //进行AddRoundKey（轮密钥加）
    assign rndout_comb= keyout^mcl;
    
       always@(posedge clk or posedge rst)begin
        if(rst)begin
            rndout <= 0;
        end
        else begin
            rndout <= rndout_comb;
        end
    end

endmodule
