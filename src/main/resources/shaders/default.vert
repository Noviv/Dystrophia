#version 330 core

uniform float timer;
 
layout(location=0) in vec4 in_Position;
layout(location=1) in vec4 in_Color;
 
out vec4 pass_Color;
 
void main(void) {
    mat3 window_scale = mat3(
        vec3(480.0/640.0, 0.0, 0.0),
        vec3(    0.0, 1.0, 0.0),
        vec3(    0.0, 0.0, 1.0)
    );
    mat3 rotation = mat3(
        vec3( cos(timer), 0.0, sin(timer)),
        vec3(-sin(timer), 0.0, cos(timer)),
        vec3(0.0, 1.0, 0.0)
    );
    mat3 constRot = mat3(
        vec3(0.0, 0.5, 0.5),
        vec3(0.0, 1.0, 0.5),
        vec3(1.0, 0.0, 0.0)
    );

    gl_Position = vec4(constRot * window_scale * rotation * in_Position.xyz, 2.0);
    pass_Color = in_Color;
}