varying vec3 color;
uniform int dx;
uniform int dy;
uniform int dz;
uniform int a;
//mat4 Translation_Matrix = mat4(
//1,0,0,0,
//0,1,0,0,
//0,0,1,0,
//dx,dy,dz,1);





void main(void)

{

	color = gl_Color.rgb;
    //gl_Position = gl_ProjectionMatrix*Translation_Matrix*gl_ModelViewMatrix*gl_Vertex;
    gl_Position = fTransform();
}