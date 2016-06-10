#version 330 core

layout(location = 0) in vec3 position;

in vec4 in_Color;
out vec4 out_Color;

uniform mat4 projection;

void main(void) {
    gl_Position = projection * vec4(position, 1.0);
    out_Color = in_Color;
}