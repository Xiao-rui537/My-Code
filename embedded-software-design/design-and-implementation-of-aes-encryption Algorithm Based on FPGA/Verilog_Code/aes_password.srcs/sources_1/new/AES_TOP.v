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
//这是顶层文件
module AES_TOP(
    clk,
    rst,
    datain,
    key,
    tempout
    );
    
    // 接收时钟信号
    input clk;
    input rst;
    // 输出加密前的信号和密钥
    output [127:0] datain;
    output [127:0] key;
    
    //用于储存并输出加加密后的数据
    output [127:0] tempout;

    assign datain = 128'h3243f6a8885a308d313198a2e0370734;
    assign key = 128'h2b7e151628aed2a6abf7158809cf4f3c;

    //实例化aescipher模块，该模块的作用是使得输入的datain原始数据，根据key密钥做AES加密，最后通过dataout输出加密后的数据
    aescipher uut1(
    .clk(clk),
    .rst(rst),
    .datain(datain),
    .key(key),
    .dataout(tempout)
    );

endmodule
