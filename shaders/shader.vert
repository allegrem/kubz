vec4 position;
uniform int display_width;
uniform int display_height;
uniform float alpha;
uniform float midle;
uniform float sens;
uniform float inv;
uniform float decalageX;
uniform float decalageY;


void main() {

	gl_FrontColor = gl_Color;
    position = gl_ModelViewProjectionMatrix * gl_Vertex;
    float falpha=float(alpha);
    float fwidth=float(display_width);
    float fheight=float(display_height);
    float echelle=2.0/fwidth;
    float echelleh=2.0/fheight;
    float fmidle=float(midle);
    vec4 vertex=position;
    float x=vertex.x;
    float y=vertex.y;
    float z=vertex.z;
    float w=vertex.w;
    float ty=y-fheight/2.0*echelleh;
    float ny=fheight*echelleh+inv*alpha*ty;
    float dx=x-fmidle*echelle+fwidth*echelle/2.0;
    float nx=fmidle*echelle-fwidth*echelle/2.0+sens*(ny)/(fheight*echelleh)*dx;
    gl_Position=vec4(nx+decalageX*echelle,y+decalageY*echelleh,z,w);
    
}