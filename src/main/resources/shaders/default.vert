#version 330 core

layout(location = 0) in vec3 in_Position;

uniform mat4 projection;
uniform mat4 model;
uniform vec3 color;

out vec3 pass_Color;

void main(void) {
    gl_Position = projection * model * vec4(in_Position, 1.0);
    pass_Color = color;
}