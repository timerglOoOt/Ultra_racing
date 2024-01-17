#version 330 core

in vec2 TexCoord;
in vec4 Color;

out vec4 FragColor;

uniform sampler2D texture1;

void main()
{
    vec4 result = Color * texture(texture1, TexCoord);
    if(result.a < 0.1)
        discard;
    FragColor = result;
    // FragColor = Color * vec4(1.0f, 0.5f, 0.2f, 1.0f);
}
