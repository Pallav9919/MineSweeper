import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
class MineSweeper extends JFrame implements ActionListener{
    JFrame f;
    JLabel X,Y,M,P,R,T;
    JTextField A,B,C;
    JButton E,Q,S,PA,Q1,PA1,Q2,OK;
    int row,column,mine,opened=0,flag=0;
    JButton[][] Grid;
    int[][] Grid1,vis;
    JDialog GO,GP,ER;
    String s;
    JMenuBar mb = new JMenuBar();
    JMenu File;
    JMenuItem i1,i2,i3;
    Image icon = Toolkit.getDefaultToolkit().getImage("Mine.jpeg");        
    MineSweeper(){
        f = new JFrame("MineSweeper");
        X = new JLabel("Enter no of columns");
        Y = new JLabel("Enter no of rows");
        M = new JLabel("Enter no of mines");
        P = new JLabel("         Please Give valid inpiut         ");
        R = new JLabel(" Mines entered are more than size of Grid ");
        T = new JLabel("Mines entered are equal to the size of Grid");
        A = new JTextField();
        B = new JTextField();
        C = new JTextField();
        E = new JButton("Start");
        Q = new JButton("Quit");
        Q1 = new JButton("Quit");
        PA = new JButton("Play Again");
        PA1 = new JButton("Play Again");
        Q2 = new JButton("Quit");
        OK = new JButton("Okay");
        File = new JMenu("File");
        i1 = new JMenuItem("Play Again");
        i2 = new JMenuItem("Restart");
        i3 = new JMenuItem("Quit");
        X.setBounds(50,50,300,25);
        A.setBounds(50,85,300,25);
        Y.setBounds(50,120,300,25);
        B.setBounds(50,155,300,25);
        M.setBounds(50,190,300,25);
        C.setBounds(50,225,300,25);
        E.setBounds(50,270,150,40);
        Q.setBounds(210,270,140,40);
        E.addActionListener(this);
        Q.addActionListener(this);
        Q1.addActionListener(this);
        PA.addActionListener(this);
        PA1.addActionListener(this);
        Q2.addActionListener(this);
        i1.addActionListener(this);
        i2.addActionListener(this);
        i3.addActionListener(this);
        OK.addActionListener(this);
        f.add(X);
        f.add(A);
        f.add(Y);
        f.add(B);
        f.add(M);
        f.add(C);
        f.add(E);
        f.add(Q);
        File.add(i1);
        File.add(i2);
        File.add(i3);
        mb.add(File);
        f.setLayout(null);
        f.setSize(400,500);
        f.setVisible(true);
        f.setJMenuBar(mb);
        f.setIconImage(icon);    
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ER = new JDialog(f,"Wrong input",Dialog.ModalityType.DOCUMENT_MODAL);
        ER.setSize(260,100);
        ER.setVisible(false);
        ER.setLayout( new FlowLayout() );
        ER.setLocationRelativeTo(f);
        ER.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        GO = new JDialog(f,"Game Over",Dialog.ModalityType.DOCUMENT_MODAL);
        GO.setSize(250,100);
        GO.add(new JLabel("Sorry, you lost this game"));
        GO.add(PA);
        GO.add(Q1);
        GO.setVisible(false);
        GO.setLocationRelativeTo(f);
        GO.setLayout( new FlowLayout() );  
        GO.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        GP = new JDialog(f,"Game Over",Dialog.ModalityType.DOCUMENT_MODAL);
        GP.setSize(250,100);
        GP.add(new JLabel("Congratulations, You won"));
        GP.add(PA1);
        GP.add(Q2);
        GP.setVisible(false);
        GP.setLocationRelativeTo(f);
        GP.setLayout( new FlowLayout() );  
        GP.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==Q || e.getSource()==Q1 || e.getSource()==Q2 || e.getSource()==i3){
            f.dispose();
        }
        else if(e.getSource()==OK){
            ER.setVisible(false);
        }
        else if(e.getSource()==PA || e.getSource()==PA1 || e.getSource()==i1){
            f.dispose();
            new MineSweeper();
            return;
        }
        else if(e.getSource()==i2){
            for(int i=0;i<row;++i)
            {
                for(int j=0;j<column;++j)
                {
                    Grid[i][j].setText("");
                    Grid[i][j].setEnabled(true);
                    vis[i][j]=0;
                    Grid1[i][j]=0;
                }
            }
            flag=0;
            opened=0;
        }
        else if(e.getSource()==E){
            String s1=A.getText();
            String s2=B.getText();
            String s3=C.getText();
            try{
                column=Integer.parseInt(s2);
                row=Integer.parseInt(s1);
                mine=Integer.parseInt(s3);
            }
            catch(NumberFormatException ex){
                ER.remove(R);
                ER.remove(T);
                ER.remove(OK);
                ER.add(P);
                ER.add(OK);
                ER.setVisible(true);
                return;
            } 
            if(column<=0 || row<=0 || column>15 || row>15 || mine<=0){
                ER.remove(R);
                ER.remove(T);
                ER.remove(OK);
                ER.add(P);
                ER.add(OK);
                ER.setVisible(true);
                return;
            }
            if(column*row<mine){
                ER.remove(P);
                ER.remove(T);
                ER.remove(OK);
                ER.add(R);
                ER.add(OK);
                ER.setVisible(true);
                return;
            }
            if(column*row==mine){
                ER.remove(P);
                ER.remove(R);
                ER.remove(OK);
                ER.add(T);
                ER.add(OK);
                ER.setVisible(true);
                return;
            }
            f.remove(X);
            f.remove(Y);
            f.remove(M);
            f.remove(A);
            f.remove(B);
            f.remove(C);
            f.remove(E);
            f.remove(Q);
            f.repaint(0,0,400,500);
            Grid = new JButton[row][column];
            Grid1 = new int[row][column];
            vis = new int[row][column];
            for(int i=0;i<row;++i)
            {
                for(int j=0;j<column;++j)
                {
                    Grid[i][j] = new JButton();
                    Grid[i][j].setBounds(i*50,j*50,50,50);
                    Grid[i][j].addActionListener(this);
                    f.add(Grid[i][j]);
                    Grid1[i][j]=0;
                    vis[i][j]=0;
                }
            }
            f.setSize(row*50+10,column*50+50+10);
        }
        else{
            Vector<Integer> Queue = new Vector<Integer>();
            for(int i=0;i<row;++i)
            {
                for(int j=0;j<column;++j)
                {
                    if(e.getSource()==Grid[i][j])
                    {
                        if(flag==0)
                        {
                            flag=1;
                            Random rand = new Random();
                            for(int i1=0;i1<mine;++i1)
                            {
                                int ra1 = rand.nextInt(row*column);
                                while(Grid1[ra1/column][ra1%column]==-1 || (ra1/column==i && ra1%column==j))
                                {
                                    ra1 = rand.nextInt(row*column);
                                }
                                Grid1[ra1/column][ra1%column]=-1;
                            }
                            for(int i1=0;i1<row;++i1)
                            {
                                for(int j1=0;j1<column;++j1)
                                {
                                    int cnt=0;
                                    if(i1!=0 && Grid1[i1-1][j1]==-1)
                                    cnt++;
                                    if(i1!=row-1 && Grid1[i1+1][j1]==-1)
                                    cnt++;
                                    if(j1!=0 && Grid1[i1][j1-1]==-1)
                                    cnt++;
                                    if(j1!=column-1 && Grid1[i1][j1+1]==-1)
                                    cnt++;
                                    if(i1!=0 && j1!=0 && Grid1[i1-1][j1-1]==-1)
                                    cnt++;
                                    if(i1!=0 && j1!=column-1 && Grid1[i1-1][j1+1]==-1)
                                    cnt++;
                                    if(i1!=row-1 && j1!=0 && Grid1[i1+1][j1-1]==-1)
                                    cnt++;
                                    if(i1!=row-1 && j1!=column-1 && Grid1[i1+1][j1+1]==-1)
                                    cnt++;
                                    if(Grid1[i1][j1]!=-1)
                                    Grid1[i1][j1]=cnt;
                                }
                            }
                        }
                        if(Grid1[i][j]==-1)
                        {
                            GO.setVisible(true);
                            return;
                        }
                        vis[i][j]=1;
                        ++opened;
                        if(Grid1[i][j]==0)
                        Queue.add(i*column+j);
                        s=Integer.toString(Grid1[i][j]);
                        if(Grid1[i][j]!=0)
                        Grid[i][j].setText(s);
                        Grid[i][j].setEnabled(false);
                        if(opened+mine==row*column)
                        {
                            GP.setVisible(true);
                            return;
                        }
                    }
                }
            }
            while(Queue.size()!=0)
            {
                int temp=Queue.remove(0);
                int i=temp/column;
                int j=temp%column;
                if(Grid1[i][j]==0)
                {
                    if(i!=0 && vis[i-1][j]==0)
                    {
                        s=Integer.toString(Grid1[i-1][j]);
                        if(Grid1[i-1][j]!=0)
                        Grid[i-1][j].setText(s);
                        Grid[i-1][j].setEnabled(false);
                        if(Grid1[i-1][j]==0)
                        Queue.add((i-1)*column+j);
                        vis[i-1][j]=1;
                        ++opened;
                    }
                    if(i!=row-1 && vis[i+1][j]==0)
                    {
                        s=Integer.toString(Grid1[i+1][j]);
                        if(Grid1[i+1][j]!=0)
                        Grid[i+1][j].setText(s);
                        Grid[i+1][j].setEnabled(false);
                        if(Grid1[i+1][j]==0)
                        Queue.add((i+1)*column+j);
                        vis[i+1][j]=1;
                        ++opened;
                    }
                    if(j!=0 && vis[i][j-1]==0)
                    {
                        s=Integer.toString(Grid1[i][j-1]);
                        if(Grid1[i][j-1]!=0)
                        Grid[i][j-1].setText(s);
                        Grid[i][j-1].setEnabled(false);
                        if(Grid1[i][j-1]==0)
                        Queue.add(i*column+j-1);
                        vis[i][j-1]=1;
                        ++opened;
                    }
                    if(j!=column-1 && vis[i][j+1]==0)
                    {
                        s=Integer.toString(Grid1[i][j+1]);
                        if(Grid1[i][j+1]!=0)
                        Grid[i][j+1].setText(s);
                        Grid[i][j+1].setEnabled(false);
                        if(Grid1[i][j+1]==0)
                        Queue.add(i*column+j+1);
                        vis[i][j+1]=1;
                        ++opened;
                    }
                    if(i!=0 && j!=0 && vis[i-1][j-1]==0)
                    {
                        s=Integer.toString(Grid1[i-1][j-1]);
                        if(Grid1[i-1][j-1]!=0)
                        Grid[i-1][j-1].setText(s);
                        Grid[i-1][j-1].setEnabled(false);
                        if(Grid1[i-1][j-1]==0)
                        Queue.add((i-1)*column+j-1);
                        vis[i-1][j-1]=1;
                        ++opened;
                    }
                    if(i!=0 && j!=column-1 && vis[i-1][j+1]==0)
                    {
                        s=Integer.toString(Grid1[i-1][j+1]);
                        if(Grid1[i-1][j+1]!=0)
                        Grid[i-1][j+1].setText(s);
                        Grid[i-1][j+1].setEnabled(false);
                        if(Grid1[i-1][j+1]==0)
                        Queue.add((i-1)*column+j+1);
                        vis[i-1][j+1]=1;
                        ++opened;
                    }
                    if(i!=row-1 && j!=0 && vis[i+1][j-1]==0)
                    {
                        s=Integer.toString(Grid1[i+1][j-1]);
                        if(Grid1[i+1][j-1]!=0)
                        Grid[i+1][j-1].setText(s);
                        Grid[i+1][j-1].setEnabled(false);
                        if(Grid1[i+1][j-1]==0)
                        Queue.add((i+1)*column+j-1);
                        vis[i+1][j-1]=1;
                        ++opened;
                    }
                    if(i!=row-1 && j!=column-1 && vis[i+1][j+1]==0)
                    {
                        s=Integer.toString(Grid1[i+1][j+1]);
                        if(Grid1[i+1][j+1]!=0)
                        Grid[i+1][j+1].setText(s);
                        Grid[i+1][j+1].setEnabled(false);
                        if(Grid1[i+1][j+1]==0)
                        Queue.add((i+1)*column+j+1);
                        vis[i+1][j+1]=1;
                        ++opened;
                    }
                }
                if(opened+mine==row*column)
                {
                    GP.setVisible(true);
                    return;
                }
            }
        }
    }
    public static void main(String args[]){
        new MineSweeper();
    }
}