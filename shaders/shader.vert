varying vec3 color;

void main(void)

{

	color = gl_Color.rgb;
    gl_Position = ftransform();

}