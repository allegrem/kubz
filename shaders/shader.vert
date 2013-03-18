vec4 position;

void main() {

	gl_FrontColor = gl_Color;
    position = gl_ModelViewProjectionMatrix * gl_Vertex;
    gl_Position=position+vec4(0,0,0,0);
    
}