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
//rounds��ģ��������ÿһ�ּ����㷨Ҫ���Ĳ��裩{һ����Ҫ10��} ÿ�ְ��������ĸ���Ҫ���裺SubBytes��S���滻����ShiftRows������λ����MixColumns���л�������AddRoundKey������Կ�ӣ�
module rounds(
    clk,
    rst,
    rc,
    data,
    keyin,
    keyout,
    rndout
    );
    
    //ʱ���ź�
    input clk;
    //��λ�ź�
    input rst;
    //�����ֳ��������е��ڼ����ˣ�
    input [3:0]rc;
    //�������ڼ��ܵ�ԭʼ����
    input [127:0]data;
    //�������ڼ��ܵ���Կ
    input [127:0]keyin;
    //����Կ�Ӻ����Կ��������һ�ֵļ�����Կ
    output [127:0]keyout;
    //���ּ��ܺ�ļ�������
    output reg [127:0]rndout = 0;

    //���ڴ���SubBytes��S���滻����ShiftRows������λ����MixColumns���л�����������
    wire [127:0] sb,sr,mcl,rndout_comb;

    //������Կ��չ
    keygeneration t0
    (
    rc,
    keyin,
    keyout
    );
    
    //����SubBytes��S���滻��
    subbytes t1
    (
    data,
    sb
     );
    
    //����ShiftRows������λ��
    shiftrow t2
    (
    sb,
    sr
    );
    
    //����MixColumns���л�����
    mixcolumn t3
    (
    sr,
    mcl
    );
      
    //����AddRoundKey������Կ�ӣ�
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
